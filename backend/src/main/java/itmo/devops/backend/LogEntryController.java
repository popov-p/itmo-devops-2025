package itmo.devops.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/logentries")
public class LogEntryController {

    @Autowired
    private LogEntryService logEntryService;

    @GetMapping
    public ResponseEntity<List<LogEntry>> getAllLogEntries() {
        List<LogEntry> logEntries = logEntryService.getAllLogEntries();
        // lol-kek
        if (logEntries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(logEntries);
    }

    @GetMapping("/api/logentries/{id}")
    public ResponseEntity<LogEntry> getLogById(@PathVariable String id) {
        Optional<LogEntry> logEntry = logEntryService.getLogEntryById(id);

        return logEntry.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<LogEntry> createLog(@RequestBody LogEntry logEntry) {
        logEntry.setTimestamp(LocalDateTime.now());
        LogEntry savedLog = logEntryService.saveLogEntry(logEntry);

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

}
