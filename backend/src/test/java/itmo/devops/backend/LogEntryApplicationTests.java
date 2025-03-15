package itmo.devops.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogEntryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testGetLogEntriesEmptyDB() {
		String url = "http://127.0.0.1:8070/api/logentries";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		assert response.getStatusCode().value() == 404;
		
		System.out.println("Response: " + response.getBody());
	}
}
