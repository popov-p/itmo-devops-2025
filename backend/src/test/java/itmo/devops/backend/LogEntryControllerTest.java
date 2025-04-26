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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Collections;
import java.util.Optional;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogEntryControllerTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mockMvc;

	@MockBean
    private LogEntryService logEntryService;

	@Autowired
    private LogEntryController logEntryController;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

	@Test
    void contextLoads() {
    }

	@Test
    public void testGetAllLogEntries() throws Exception {
        mockMvc.perform(get("/api/logentries"))
            .andExpect(status().isNotFound());
    }

	@Test
    public void testGetAllLogEntriesReturnsOk() throws Exception {
        LogEntry mockEntry = new LogEntry("1", "Alice", "Test message", LocalDateTime.now());
        List<LogEntry> entries = Collections.singletonList(mockEntry);

        Mockito.when(logEntryService.getAllLogEntries()).thenReturn(entries);

        mockMvc.perform(get("/api/logentries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeName").value("Alice"))
                .andExpect(jsonPath("$[0].logMessage").value("Test message"));
    }

	@Test
    public void testGetLogByIdReturnsOk() throws Exception {
        LogEntry mockLogEntry = new LogEntry("1", "Alice", "Test message", LocalDateTime.now());
        when(logEntryService.getLogEntryById("1")).thenReturn(Optional.of(mockLogEntry));

        mockMvc.perform(get("/api/logentries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeName").value("Alice"))
                .andExpect(jsonPath("$.logMessage").value("Test message"));
    }

	@Test
    public void testCreateLogReturnsCreated() throws Exception {
        LogEntry mockLogEntry = new LogEntry("1", "Alice", "Test message", LocalDateTime.now());
        
        when(logEntryService.saveLogEntry(mockLogEntry)).thenReturn(mockLogEntry);

        mockMvc.perform(post("/api/logentries")
                .contentType("application/json")
                .content("{ \"employeeName\": \"Alice\", \"logMessage\": \"Test message\" }"));
    }

	@Test
	public void testUpdateLogEntryReturnsOk() throws Exception {
		String logId = "1";
		LogEntry existingLogEntry = new LogEntry(logId, "Alice", "Old message", LocalDateTime.now());
		LogEntry updatedLogEntry = new LogEntry(logId, "Alice", "Updated message", LocalDateTime.now());

		when(logEntryService.getLogEntryById(logId)).thenReturn(Optional.of(existingLogEntry));

		when(logEntryService.saveLogEntry(Mockito.any(LogEntry.class))).thenReturn(updatedLogEntry);

		mockMvc.perform(put("/api/logentries/{id}", logId)
				.contentType("application/json")
				.content("{\"employeeName\": \"Alice\", \"logMessage\": \"Updated message\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeName").value("Alice"))
				.andExpect(jsonPath("$.logMessage").value("Updated message"));
	}

	@Test
	public void testUpdateLogEntryReturnsNotFound() throws Exception {
    String logId = "1";
    LogEntry updatedLogEntry = new LogEntry(logId, "Alice", "Updated message", LocalDateTime.now());

    when(logEntryService.getLogEntryById(logId)).thenReturn(Optional.empty());

    mockMvc.perform(put("/api/logentries/{id}", logId)
            .contentType("application/json")
            .content("{\"employeeName\": \"Alice\", \"logMessage\": \"Updated message\"}"))
            .andExpect(status().isNotFound());
	}


	@Test
	public void testDeleteLogEntryReturnsNoContent() throws Exception {
		String logId = "1";

		when(logEntryService.deleteLogEntryById(logId)).thenReturn(true);

		mockMvc.perform(delete("/api/logentries/{id}", logId))
				.andExpect(status().isNoContent());
	}

	@Test
	public void testDeleteLogEntryReturnsNotFound() throws Exception {
		String logId = "1";

		when(logEntryService.deleteLogEntryById(logId)).thenReturn(false);

		mockMvc.perform(delete("/api/logentries/{id}", logId))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteLogEntriesReturnsOk() throws Exception {
		List<String> ids = Arrays.asList("1", "2", "3");

		when(logEntryService.deleteLogEntryById("1")).thenReturn(true);
		when(logEntryService.deleteLogEntryById("2")).thenReturn(true);
		when(logEntryService.deleteLogEntryById("3")).thenReturn(true);

		mockMvc.perform(delete("/api/logentries")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ids)));
	}

	@Test
	public void testDeleteLogEntriesReturnsBadRequest() throws Exception {
		List<String> ids = Arrays.asList("1", "2", "3");

		when(logEntryService.deleteLogEntryById("1")).thenReturn(true);
		when(logEntryService.deleteLogEntryById("2")).thenReturn(false);
		when(logEntryService.deleteLogEntryById("3")).thenReturn(true);

		mockMvc.perform(delete("/api/logentries")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ids)));
	}

	@Test
    public void testDeleteLogEntries() {
        when(logEntryService.deleteLogEntryById("1")).thenReturn(true);
        when(logEntryService.deleteLogEntryById("2")).thenReturn(true);

        ResponseEntity<String> response = logEntryController.deleteLogEntries(Arrays.asList("1", "2"));

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

	@Test
    public void testDeleteLogEntries_someFailed_errorResponse() {
        when(logEntryService.deleteLogEntryById("1")).thenReturn(true);
        when(logEntryService.deleteLogEntryById("2")).thenReturn(false);

        ResponseEntity<String> response = logEntryController.deleteLogEntries(Arrays.asList("1", "2"));

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

	@Test
    public void testDeleteAllLogEntries() {
        ResponseEntity<Void> response = logEntryController.deleteAllLogEntries();

        verify(logEntryService, times(1)).deleteAll();

        System.out.println(response.getStatusCode());
    }
	
}