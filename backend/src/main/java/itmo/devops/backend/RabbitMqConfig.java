package itmo.devops.backend;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue logEntryQueue() {
        return new Queue("LogEntryQueue", true);
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange("");
    }

    @Bean
    public Binding bindingLogEntryQueue(Queue logEntryQueue, DirectExchange defaultExchange) {
        return BindingBuilder.bind(logEntryQueue)
                .to(defaultExchange)
                .with("LogEntryQueue");
    }
}
