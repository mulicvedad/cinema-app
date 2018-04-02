package ba.unsa.etf.nwt.movieservice;

import ba.unsa.etf.nwt.movieservice.repository.ReviewRepository;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;

@Component
public class Receiver {
    @Autowired
    ReviewRepository reviewRepository;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = "usersMovieQueue", durable = "false"),
            exchange = @Exchange(value = "users-exchange", type = "topic")
    ))
    public void deleteUser(Long id) throws ServletException {
        System.out.println(id);
        reviewRepository.deleteByUserId(id);
    }
}
