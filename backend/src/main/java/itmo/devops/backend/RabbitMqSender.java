package itmo.devops.backend;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@Service
public class RabbitMqSender {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public RabbitMqSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public void sendMessage(String name, String text) {
        try {
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("name", name);
            messageData.put("text", text);
            messageData.put("timestamp", Instant.now().getEpochSecond());

            String jsonMessage = objectMapper.writeValueAsString(messageData);

            String queueName = "LogEntryQueue";
            rabbitTemplate.convertAndSend(queueName, jsonMessage);

        } catch (Exception e) {
            logger.error("Error while sending message to RabbitMQ", e);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);
}
