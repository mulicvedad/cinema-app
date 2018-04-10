package ba.unsa.etf.nwt.movieservice;

import ba.unsa.etf.nwt.movieservice.repository.ReviewRepository;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @Autowired
    ReviewRepository reviewRepository;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = "usersMovieQueue", durable = "false"),
            exchange = @Exchange(value = "users-exchange", type = "topic")
    ))
    public void deleteUserInformation(Long id) {
        reviewRepository.deleteByUserId(id);
    }
}
