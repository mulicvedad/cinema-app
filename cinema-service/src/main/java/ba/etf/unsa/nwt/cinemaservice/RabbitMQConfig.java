package ba.etf.unsa.nwt.cinemaservice;

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
        return new Queue("usersCinemaQueue", false);
    }

    @Bean
    public TopicExchange usersExchange() {
        return new TopicExchange("users-exchange");
    }

    @Bean
    public Binding usersBinding(Queue usersQueue, TopicExchange usersExchange) {
        return BindingBuilder.bind(usersQueue).to(usersExchange).with("users.*");
    }


    @Bean
    public Queue moviesDeletedQueue() {
        return new Queue("moviesDeletedQueue", false);
    }

    @Bean
    TopicExchange moviesExchange() {
        return new TopicExchange("movies-exchange");
    }

    @Bean
    public Binding moviesDeletedBinding(Queue moviesDeletedQueue, TopicExchange moviesExchange) {
        return BindingBuilder.bind(moviesDeletedQueue).to(moviesExchange).with("movies.deleted.#");
    }

    /* Ukoliko se implementira mogućnost izmjene movie entiteta
    @Bean
    public Queue moviesUpdatedQueue() {
        return new Queue("moviesUpdatedQueue", false);
    }

    @Bean
    public Binding moviesUpdatedBinding(Queue moviesUpdatedQueue, TopicExchange moviesExchange) {
        return BindingBuilder.bind(moviesUpdatedQueue).to(moviesExchange).with("movies.updated.#");
    }
    */

}
