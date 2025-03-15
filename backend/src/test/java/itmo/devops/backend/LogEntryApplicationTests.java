package itmo.devops.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogEntryApplicationTests {

    // @LocalServerPort
    // private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test 
	void testGetAllLogEntries() {
        // Отправляем GET-запрос к /api/logentries
        ResponseEntity<LogEntry[]> response = restTemplate.getForEntity(
                "http://localhost:" + "8070" + "/api/logentries",
                LogEntry[].class
        );

        // Проверяем статус ответа
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // @Test
    // void testCreateLog() {
    //     // Создаем тестовую запись
    //     LogEntry logEntry = new LogEntry(null, "John Doe", "Test log", LocalDateTime.now());

    //     // Отправляем POST-запрос к /api/logentries
    //     ResponseEntity<LogEntry> response = restTemplate.postForEntity(
    //             "http://localhost:" + port + "/api/logentries",
    //             logEntry,
    //             LogEntry.class
    //     );

	//     // Проверяем статус ответа и данные
    //     assertEquals(HttpStatus.CREATED, response.getStatusCode());
    //     assertEquals("John Doe", response.getBody().getEmployeeName());
    //     assertEquals("Test log", response.getBody().getLogMessage());
    // }

	// @Test

	// @Test
    // void testGetLogById() {
    //     // Создаем тестовую запись
    //     LogEntry logEntry = new LogEntry("4", "John Doe", "Test log",  LocalDateTime.now());
	// 	// logEntry.setTimestamp(LocalDateTime.now());
    //     restTemplate.postForEntity(
    //             "http://localhost:" + "8070" + "/api/logentries",
    //             logEntry,
    //             LogEntry.class
    //     );

    //     // Отправляем GET-запрос к /api/logentries/1
    //     ResponseEntity<LogEntry> response = restTemplate.getForEntity(
    //             "http://localhost:" + "8070" + "/api/logentries/" + "4",
    //             LogEntry.class
    //     );

    //     // Проверяем статус ответа и данные
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     // assertEquals(logEntry.getId(), response.getBody().getId());
    //     assertEquals("John Doe", response.getBody().getEmployeeName());
    //     assertEquals("Test log", response.getBody().getLogMessage());
    // }


    // @Test
    // void testUpdateLogEntry() {
    //     // Создаем тестовую запись
    //     LogEntry logEntry = new LogEntry("1", "John Doe", "Old log", LocalDateTime.now());
    //     restTemplate.postForEntity(
    //             "http://localhost:" + port + "/api/logentries",
    //             logEntry,
    //             LogEntry.class
    //     );

    //     // Обновляем запись
    //     LogEntry updatedLogEntry = new LogEntry("1", "John Doe", "Updated log", LocalDateTime.now());
    //     restTemplate.put(
    //             "http://localhost:" + port + "/api/logentries/1",
    //             updatedLogEntry
    //     );

    //     // Получаем обновленную запись
    //     ResponseEntity<LogEntry> response = restTemplate.getForEntity(
    //             "http://localhost:" + port + "/api/logentries/1",
    //             LogEntry.class
    //     );

    //     // Проверяем статус ответа и данные
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals("Updated log", response.getBody().getLogMessage());
    // }

    // @Test
    // void testDeleteLogEntry() {
    //     // Создаем тестовую запись
    //     LogEntry logEntry = new LogEntry("1", "John Doe", "Test log", LocalDateTime.now());
    //     restTemplate.postForEntity(
    //             "http://localhost:" + port + "/api/logentries",
    //             logEntry,
    //             LogEntry.class
    //     );

    //     // Удаляем запись
    //     restTemplate.delete("http://localhost:" + port + "/api/logentries/1");

    //     // Пытаемся получить удаленную запись
    //     ResponseEntity<LogEntry> response = restTemplate.getForEntity(
    //             "http://localhost:" + port + "/api/logentries/1",
    //             LogEntry.class
    //     );

    //     // Проверяем, что запись не найдена
    //     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    // }

}
