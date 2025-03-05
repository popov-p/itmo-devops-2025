package itmo.devops.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "itmo.devops.backend")
public class LogEntryApplication {
	public static void main(String[] args) {
		SpringApplication.run(LogEntryApplication.class, args);
	}

}
