package itmo.devops.backend;
import itmo.devops.backend.LogEntryMessage.msg;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.protobuf.Timestamp;

import java.time.Instant;


@Service
public class RabbitMqSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendMessage(String name, String text) {
        try {
            msg.Builder messageBuilder = msg.newBuilder()
                    .setName(name)
                    .setText(text)
                    .setTimestamp(Timestamp.newBuilder().setSeconds(Instant.now().getEpochSecond()).build());

            byte[] messageBytes = messageBuilder.build().toByteArray();

            String queueName = "LogEntryQueue";
            rabbitTemplate.convertAndSend(queueName, messageBytes);

        } catch (Exception e) {
            logger.error("Error while sending message to RabbitMQ", e);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);
}
