package ba.unsa.etf.nwt.movieservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue usersQueue() {
        return new Queue("usersMovieQueue", false);
    }

    @Bean
    public TopicExchange usersExchange() {
        return new TopicExchange("users-exchange");
    }

    @Bean
    public Binding usersBinding(Queue usersQueue, TopicExchange usersExchange) {
        return BindingBuilder.bind(usersQueue).to(usersExchange).with("users.*");
    }
}
