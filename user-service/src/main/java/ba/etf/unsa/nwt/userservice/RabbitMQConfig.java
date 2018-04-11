package ba.etf.unsa.nwt.userservice;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    static final String topicExchangeName = "users-exchange";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }
}
