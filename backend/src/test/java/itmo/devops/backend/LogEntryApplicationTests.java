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
}
