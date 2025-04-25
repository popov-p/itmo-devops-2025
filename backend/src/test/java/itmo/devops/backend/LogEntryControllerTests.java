package itmo.devops.backend;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.mockito.*;
import static org.mockito.Mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogEntryControllerTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

	@BeforeEach
	void clearDatabase() {
		String url = "http://localhost:8070/api/logentries/all";
		ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

		assertEquals(204, response.getStatusCode().value());
	}

	@Test
    void contextLoads() {
    }

	// @Test
	// void testGetLogEntriesEmptyDB() {
	// 	String url = "http://localhost:8070/api/logentries";

	// 	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

	// 	assert response.getStatusCode().value() == 404;

	// 	System.out.println("Response: " + response.getBody());
	// }

	// @Test
	// void testGetLogEntriesNotEmptyDB() {
	// 	String url = "http://localhost:8070/api/logentries";

	// 	String jsonBody = "{\n" +
	// 			"    \"employeeName\": \"Pavel Popov\",\n" +
	// 			"    \"logMessage\": \"Hello world\"\n" +
	// 			"}";

	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.set("Content-Type", "application/json");

	// 	HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

	// 	ResponseEntity<String> post_response = restTemplate
	// 			.postForEntity(url, entity, String.class);

	// 	assertEquals(201, post_response.getStatusCode().value());

	// 	ResponseEntity<String> get_response = restTemplate.getForEntity(url, String.class);

	// 	assert get_response.getStatusCode().value() == 200;

	// 	System.out.println("Response: " + get_response.getBody());
	// }

	// @Test
	// void testCreateLogEntry() {
	// 	String url = "http://localhost:8070/api/logentries";

	// 	String jsonBody = "{\n" +
	// 			"    \"employeeName\": \"Pavel Popov\",\n" +
	// 			"    \"logMessage\": \"Hello world\"\n" +
	// 			"}";

	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.set("Content-Type", "application/json");

	// 	HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

	// 	ResponseEntity<String> response = restTemplate
	// 			.postForEntity(url, entity, String.class);

	// 	assertEquals(201, response.getStatusCode().value());

	// 	System.out.println("Response: " + response.getBody());
	// }

	// @Test
	// void testUpdateLogEntry() throws Exception {
	// 	String url = "http://localhost:8070/api/logentries";

	// 	// Создаём запись
	// 	String jsonBody = "{\n" +
	// 			"    \"id\": 1,\n" +
	// 			"    \"employeeName\": \"Pavel Popov\",\n" +
	// 			"    \"logMessage\": \"Hello world\"\n" +
	// 			"}";

	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.setContentType(MediaType.APPLICATION_JSON);

	// 	HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
	// 	ResponseEntity<String> postResponse = restTemplate.postForEntity(url, entity, String.class);
	// 	assertEquals(201, postResponse.getStatusCode().value());

	// 	String updateUrl = url + "/1";

	// 	String updatedJsonBody = "{\n" +
	// 			"    \"id\": 1,\n" +
	// 			"    \"employeeName\": \"Pavel Popov\",\n" +
	// 			"    \"logMessage\": \"Updated message\"\n" +
	// 			"}";

	// 	HttpEntity<String> updateEntity = new HttpEntity<>(updatedJsonBody, headers);
	// 	ResponseEntity<String> putResponse = restTemplate.exchange(updateUrl, HttpMethod.PUT, updateEntity, String.class);
	// 	assertEquals(200, putResponse.getStatusCode().value());

	// 	JsonNode jsonNode = objectMapper.readTree(putResponse.getBody());
	// 	String actualLogMessage = jsonNode.get("logMessage").asText();

	// 	assertEquals("Updated message", actualLogMessage, "logMessage не обновился!");

	// 	System.out.println("Updated Response: " + putResponse.getBody());
	// }

	// @Test
	// void testDeleteLogEntry() throws Exception {
	// 	String url = "http://localhost:8070/api/logentries";
	// 	String logId = "1";

	// 	String jsonBody = "{\n" +
	// 			"    \"id\": " + logId + ",\n" +
	// 			"    \"employeeName\": \"Pavel Popov\",\n" +
	// 			"    \"logMessage\": \"Hello world\"\n" +
	// 			"}";

	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.setContentType(MediaType.APPLICATION_JSON);

	// 	HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
	// 	ResponseEntity<String> postResponse = restTemplate.postForEntity(url, entity, String.class);
	// 	assertEquals(201, postResponse.getStatusCode().value(), "Запись не была создана!");

	// 	String deleteUrl = url + "/" + logId;

	// 	ResponseEntity<Void> deleteResponse = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, Void.class);
	// 	assertEquals(204, deleteResponse.getStatusCode().value(), "Запись не удалилась!");

	// 	ResponseEntity<String> getResponse = restTemplate.getForEntity(deleteUrl, String.class);
	// 	assertEquals(404, getResponse.getStatusCode().value(), "Удалённая запись всё ещё существует!");

	// 	System.out.println("Delete Response: " + deleteResponse.getStatusCode());
	// }

	// @Test
	// void testCreateUpdateAndDeleteLogEntry() throws Exception {
	// 	String url = "http://localhost:8070/api/logentries";
	// 	String logId = "1";

	// 	// 1️⃣ Создаём запись
	// 	String jsonBody = "{\n" +
	// 			"    \"id\": " + logId + ",\n" +
	// 			"    \"employeeName\": \"Pavel Popov\",\n" +
	// 			"    \"logMessage\": \"Hello world\"\n" +
	// 			"}";

	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.setContentType(MediaType.APPLICATION_JSON);

	// 	HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
	// 	ResponseEntity<String> postResponse = restTemplate.postForEntity(url, entity, String.class);
	// 	assertEquals(201, postResponse.getStatusCode().value(), "Запись не была создана!");

	// 	String updateUrl = url + "/" + logId;
	// 	String updatedJsonBody = "{\n" +
	// 			"    \"id\": " + logId + ",\n" +
	// 			"    \"employeeName\": \"Pavel Popov\",\n" +
	// 			"    \"logMessage\": \"Updated message\"\n" +
	// 			"}";

	// 	HttpEntity<String> updateEntity = new HttpEntity<>(updatedJsonBody, headers);
	// 	ResponseEntity<String> putResponse = restTemplate.exchange(updateUrl, HttpMethod.PUT, updateEntity, String.class);
	// 	assertEquals(200, putResponse.getStatusCode().value(), "Обновление не прошло!");

	// 	JsonNode jsonNode = objectMapper.readTree(putResponse.getBody());
	// 	String actualLogMessage = jsonNode.get("logMessage").asText();
	// 	assertEquals("Updated message", actualLogMessage, "logMessage не обновился!");

	// 	ResponseEntity<Void> deleteResponse = restTemplate.exchange(updateUrl, HttpMethod.DELETE, null, Void.class);
	// 	assertEquals(204, deleteResponse.getStatusCode().value(), "Запись не удалилась!");

	// 	ResponseEntity<String> getResponse = restTemplate.getForEntity(updateUrl, String.class);
	// 	assertEquals(404, getResponse.getStatusCode().value(), "Удалённая запись всё ещё существует!");

	// 	System.out.println("Delete Response: " + deleteResponse.getStatusCode());
	// }

	@Test
    public void testGetAllLogEntries() throws Exception {
        mockMvc.perform(get("/api/logentries"))
            .andExpect(status().isNotFound());
    }
	
}


class LogEntryTest {

    private LogEntry logEntry;
    private LocalDateTime timestamp;

    @BeforeEach
    void setUp() {
        timestamp = LocalDateTime.of(2025, 4, 25, 10, 30, 0);
        logEntry = new LogEntry("1", "John Doe", "Test log message", timestamp);
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(logEntry);
        assertEquals("1", logEntry.getId());
        assertEquals("John Doe", logEntry.getEmployeeName());
        assertEquals("Test log message", logEntry.getLogMessage());
        assertEquals(timestamp, logEntry.getTimestamp());
    }

    @Test
    void testSetters() {
        logEntry.setId("2");
        logEntry.setEmployeeName("Jane Doe");
        logEntry.setLogMessage("Updated log message");
        logEntry.setTimestamp(LocalDateTime.of(2025, 4, 25, 12, 0, 0));

        assertEquals("2", logEntry.getId());
        assertEquals("Jane Doe", logEntry.getEmployeeName());
        assertEquals("Updated log message", logEntry.getLogMessage());
        assertEquals(LocalDateTime.of(2025, 4, 25, 12, 0, 0), logEntry.getTimestamp());
    }

    @Test
    void testTimestampFormat() {
        LocalDateTime expectedTimestamp = LocalDateTime.of(2025, 4, 25, 10, 30, 0);
        logEntry.setTimestamp(expectedTimestamp);
        assertEquals(expectedTimestamp, logEntry.getTimestamp());
    }
}

