package itmo.devops.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogEntryService {

    @Autowired
    private LogEntryRepository logEntryRepository;

    public List<LogEntry> getAllLogEntries() {
        return logEntryRepository.findAll();
    }

    public Optional<LogEntry> getLogEntryById(String id) {
        return logEntryRepository.findById(id);
    }

    public LogEntry saveLogEntry(LogEntry logEntry) {
        return logEntryRepository.save(logEntry);
    }

    public boolean deleteLogEntryById(String id) {
        logger.info("Service: Attempting to delete log entry with id: {}", id);

        if (!logEntryRepository.existsById(id)) {
            logger.warn("Service: Log entry with id: {} does not exist", id);
            return false;
        }

        logEntryRepository.deleteById(id);
        logger.info("Service: Log entry with id: {} successfully deleted", id);

        return true;
    }

    public void deleteAll() {
        logEntryRepository.deleteAll();
    }


    private static final Logger logger = LoggerFactory.getLogger(LogEntryService.class);

}
