package itmo.devops.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testCreateProduct() {
		Product product = new Product("nnnmx", "Test Product", 777.0);

		ResponseEntity<Product> response = restTemplate.postForEntity("/api/products", product, Product.class);

		assertEquals(200, response.getStatusCode().value());
		assertEquals("Test Product", Objects.requireNonNull(response.getBody()).getName());
		assertEquals(777.0, response.getBody().getPrice());
	}

	@Test
	void testGetProductById() {
		// Создаём продукт через POST запрос для теста
		Product product = new Product("nnnmxt","bfgb", 50.0);
		ResponseEntity<Product> postResponse = restTemplate.postForEntity("/api/products", product, Product.class);
		String productId = Objects.requireNonNull(postResponse.getBody()).getId();

		// Получаем продукт по ID
		ResponseEntity<Product> response = restTemplate.getForEntity("/api/products/" + productId, Product.class);

		// Проверка, что ответ имеет статус 200 (OK)
		assertEquals(200, response.getStatusCode().value());
	}
}
