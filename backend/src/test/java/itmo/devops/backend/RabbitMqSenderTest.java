package itmo.devops.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

class RabbitMqSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RabbitMqSender rabbitMqSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() throws Exception {
        // Подготавлива                                     ем тестовые данные
        String name = "testName";
        String text = "testText";

        // Мокаем сериализацию объекта в JSON
        Mockito.when(objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"testName\",\"text\":\"testText\",\"timestamp\":1234567890}");

        // Вызываем метод отправки сообщения
        rabbitMqSender.sendMessage(name, text);

        // Проверяем, что метод отправки сообщения был вызван
        verify(rabbitTemplate).convertAndSend(Mockito.eq("LogEntryQueue"), Mockito.anyString());
    }

    @Test
    void testSendMessageException() throws Exception {
        // Подготавливаем тестовые данные
        String name = "testName";
        String text = "testText";

        // Мокаем исключение при сериализации
        Mockito.when(objectMapper.writeValueAsString(Mockito.any())).thenThrow(new RuntimeException("Serialization error"));

        // Вызываем метод и проверяем, что исключение обработано
        rabbitMqSender.sendMessage(name, text);

        // Проверяем, что не было попытки отправить сообщение в RabbitMQ
        verify(rabbitTemplate, Mockito.never()).convertAndSend(Mockito.eq("LogEntryQueue"), Mockito.anyString());
    }
}
