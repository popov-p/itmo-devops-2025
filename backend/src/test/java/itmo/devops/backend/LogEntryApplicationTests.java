package itmo.devops.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogEntryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

//	@Test
//	void testGetLogEntriesEmptyDB() {
//		String url = "http://127.0.0.1:8070/api/logentries";
//
//		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//		assert response.getStatusCode().value() == 404;
//
//		System.out.println("Response: " + response.getBody());
//	}

	@Test
	void testCreateLogEntry() {
		String url = "http://127.0.0.1:8070/api/logentries";

		String jsonBody = "{\n" +
				"    \"employeeName\": \"Pavel Popov\",\n" +
				"    \"logMessage\": \"Hello world\"\n" +
				"}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity(url, entity, String.class);

		assertEquals(201, response.getStatusCode().value());

		System.out.println("Response: " + response.getBody());
	}

	@Test
	void testGetLogEntriesNotEmptyDB() {
		String url = "http://127.0.0.1:8070/api/logentries";

		String jsonBody = "{\n" +
				"    \"employeeName\": \"Pavel Popov\",\n" +
				"    \"logMessage\": \"Hello world\"\n" +
				"}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

		ResponseEntity<String> post_response = restTemplate
				.postForEntity(url, entity, String.class);

		assertEquals(201, post_response.getStatusCode().value());

		ResponseEntity<String> get_response = restTemplate.getForEntity(url, String.class);

		assert get_response.getStatusCode().value() == 200;

		System.out.println("Response: " + get_response.getBody());
	}
}