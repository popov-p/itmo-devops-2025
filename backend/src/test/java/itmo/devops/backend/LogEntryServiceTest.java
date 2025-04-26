package itmo.devops.backend;
import itmo.devops.backend.LogEntry;
import itmo.devops.backend.LogEntryRepository;
import itmo.devops.backend.LogEntryService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogEntryServiceTest {

    @Mock
    private LogEntryRepository logEntryRepository;

    @InjectMocks
    private LogEntryService logEntryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLogEntries() {
        LogEntry entry1 = new LogEntry("1", "John Doe", "Test message 1", LocalDateTime.now());
        LogEntry entry2 = new LogEntry("2", "Jane Doe", "Test message 2", LocalDateTime.now());
        List<LogEntry> mockEntries = Arrays.asList(entry1, entry2);

        when(logEntryRepository.findAll()).thenReturn(mockEntries);

        List<LogEntry> result = logEntryService.getAllLogEntries();

        assertEquals(2, result.size());
        verify(logEntryRepository, times(1)).findAll();
    }

    @Test
    void testDeleteLogEntryById_NotFound() {
        String id = "123";

        when(logEntryRepository.existsById(id)).thenReturn(false);

        boolean result = logEntryService.deleteLogEntryById(id);

        assertFalse(result);
        verify(logEntryRepository, times(1)).existsById(id);
        verify(logEntryRepository, times(0)).deleteById(id);
    }

    @Test
    void testDeleteLogEntryById_FoundAndDeleted() {
        String id = "123";

        when(logEntryRepository.existsById(id)).thenReturn(true);

        boolean result = logEntryService.deleteLogEntryById(id);

        assertTrue(result);
        verify(logEntryRepository, times(1)).existsById(id);
        verify(logEntryRepository, times(1)).deleteById(id);
    }
}
