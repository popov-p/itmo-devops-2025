package itmo.devops.backend;
import itmo.devops.backend.dto.LogEntryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        String sanitizedPodName = podName.replaceAll("[\\n\\r]", "_");
        this.getAllLogentriesRequestCounter = Counter.builder("logentries_get_requests_total")
                                                      .tag("type", "GET")
                                                      .tag("pod", sanitizedPodName)
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
    public ResponseEntity<LogEntry> createLog(@RequestBody LogEntryDto logEntryDto) {
        LogEntry logEntry = new LogEntry();
        logEntry.setEmployeeName(logEntryDto.getEmployeeName());
        logEntry.setLogMessage(logEntryDto.getLogMessage());
        logEntry.setTimestamp(LocalDateTime.now());

        LogEntry savedLog = logEntryService.saveLogEntry(logEntry);

        rabbitMqSender.sendMessage(savedLog.getEmployeeName(), savedLog.getLogMessage());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedLog);
    }


    @PutMapping("/{id}")
public ResponseEntity<LogEntryDto> updateLogEntry(@PathVariable String id, @RequestBody LogEntryDto logEntryDto) {
    Optional<LogEntry> existingLog = logEntryService.getLogEntryById(id);

    if (existingLog.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    LogEntry updatedEntity = new LogEntry(
        id,
        logEntryDto.getEmployeeName(),
        logEntryDto.getLogMessage(),
        LocalDateTime.now()
    );

    LogEntry saved = logEntryService.saveLogEntry(updatedEntity);

    LogEntryDto responseDto = new LogEntryDto();
    responseDto.setEmployeeName(saved.getEmployeeName());
    responseDto.setLogMessage(saved.getLogMessage());

    return ResponseEntity.ok(responseDto);
}



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogEntry(@PathVariable String id) {
        if (!isValidMongoId(id)) {
            return ResponseEntity.badRequest().build();
        }
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
            if (!isValidMongoId(id)) {
                return ResponseEntity.badRequest().body("Invalid ID format: " + id);
            }
        }
        
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

    private boolean isValidMongoId(String id) {
        return id != null && id.matches("[a-f0-9]{1,24}");
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
