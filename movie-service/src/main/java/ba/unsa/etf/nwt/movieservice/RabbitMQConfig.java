package ba.unsa.etf.nwt.movieservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String MOVIES_EXCHANGE = "movies-exchange";
    private static final String USERS_EXCHANGE = "users-exchange";
    private static final String USERS_MOVIE_QUEUE = "usersMovieQueue";

    @Bean
    TopicExchange moviesExchange() {
        return new TopicExchange(MOVIES_EXCHANGE);
    }

    @Bean
    public Queue usersQueue() {
        return new Queue(USERS_MOVIE_QUEUE, false);
    }

    @Bean
    public TopicExchange usersExchange() {
        return new TopicExchange(USERS_EXCHANGE);
    }

    @Bean
    public Binding usersBinding(Queue usersQueue, TopicExchange usersExchange) {
        return BindingBuilder.bind(usersQueue).to(usersExchange).with("users.*");

    }

}
