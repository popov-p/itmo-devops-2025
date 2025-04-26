package itmo.devops.backend;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

// class LogEntryTest {

//     private LogEntry logEntry;
//     private LocalDateTime timestamp;

//     @BeforeEach
//     void setUp() {
//         timestamp = LocalDateTime.of(2025, 4, 25, 10, 30, 0);
//         logEntry = new LogEntry("1", "John Doe", "Test log message", timestamp);
//     }

//     @Test
//     void testConstructorAndGetters() {
//         assertNotNull(logEntry);
//         assertEquals("1", logEntry.getId());
//         assertEquals("John Doe", logEntry.getEmployeeName());
//         assertEquals("Test log message", logEntry.getLogMessage());
//         assertEquals(timestamp, logEntry.getTimestamp());
//     }

//     @Test
//     void testSetters() {
//         logEntry.setId("2");
//         logEntry.setEmployeeName("Jane Doe");
//         logEntry.setLogMessage("Updated log message");
//         logEntry.setTimestamp(LocalDateTime.of(2025, 4, 25, 12, 0, 0));

//         assertEquals("2", logEntry.getId());
//         assertEquals("Jane Doe", logEntry.getEmployeeName());
//         assertEquals("Updated log message", logEntry.getLogMessage());
//         assertEquals(LocalDateTime.of(2025, 4, 25, 12, 0, 0), logEntry.getTimestamp());
//     }

//     @Test
//     void testTimestampFormat() {
//         LocalDateTime expectedTimestamp = LocalDateTime.of(2025, 4, 25, 10, 30, 0);
//         logEntry.setTimestamp(expectedTimestamp);
//         assertEquals(expectedTimestamp, logEntry.getTimestamp());
//     }
// }