package itmo.devops.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/logentries")
public class LogEntryController {

    @Autowired
    private LogEntryService logEntryService;

    private final Counter getAllLogentriesRequestCounter;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    public LogEntryController(MeterRegistry registry, @Value("${POD_NAME:unknown}") String podName) {
        this.getAllLogentriesRequestCounter = Counter.builder("logentries_get_requests_total")
                                                      .tag("type", "GET")
                                                      .tag("pod", podName)
                                                      .register(registry);
    }

    @GetMapping
    public ResponseEntity<List<LogEntry>> getAllLogEntries() {
        getAllLogentriesRequestCounter.increment();
        List<LogEntry> logEntries = logEntryService.getAllLogEntries();
        if (logEntries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(logEntries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogEntry> getLogById(@PathVariable String id) {
        Optional<LogEntry> logEntry = logEntryService.getLogEntryById(id);

        return logEntry.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<LogEntry> createLog(@RequestBody LogEntry logEntry) {
        logEntry.setTimestamp(LocalDateTime.now());
        LogEntry savedLog = logEntryService.saveLogEntry(logEntry);

        rabbitMqSender.sendMessage(savedLog.getEmployeeName(), savedLog.getLogMessage());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedLog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogEntry> updateLogEntry(@PathVariable String id, @RequestBody LogEntry logEntry) {
        Optional<LogEntry> existingLog = logEntryService.getLogEntryById(id);

        if (existingLog.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        logEntry.setId(id);
        logEntry.setTimestamp(LocalDateTime.now());
        LogEntry updatedLog = logEntryService.saveLogEntry(logEntry);

        return ResponseEntity.ok(updatedLog);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogEntry(@PathVariable String id) {
        boolean deleted = logEntryService.deleteLogEntryById(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/logentries")
    public ResponseEntity<String> deleteLogEntries(@RequestBody List<String> ids) {
        boolean allDeleted = true;

        for (String id : ids) {
            if (!logEntryService.deleteLogEntryById(id)) {
                allDeleted = false;
            }
        }

        if (allDeleted) {
            logger.info("Controller: All log entries successfully deleted.");
            return ResponseEntity.ok("All log entries successfully deleted.");
        } else {
            logger.warn("Controller: Some log entries could not be deleted.");
            return ResponseEntity.status(400).body("Some log entries could not be deleted.");
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllLogEntries() {
        logEntryService.deleteAll();
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("Backend is up and running");
    }

    private static final Logger logger = LoggerFactory.getLogger(LogEntryController.class);

}
