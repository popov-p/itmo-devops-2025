package itmo.devops.backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "LogEntries")
public class LogEntry {

    @Id
    private String id;
    private String employeeName;
    private String logMessage;
    private LocalDateTime timestamp;

    public LogEntry(String id, String employeeName, String logMessage, LocalDateTime timestamp) {
        this.id = id;
        this.employeeName = employeeName;
        this.logMessage = logMessage;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
