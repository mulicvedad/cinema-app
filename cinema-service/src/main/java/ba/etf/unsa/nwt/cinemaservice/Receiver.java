package ba.etf.unsa.nwt.cinemaservice;

import ba.etf.unsa.nwt.cinemaservice.services.ReservationService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    ReservationService reservationService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = "usersCinemaQueue", durable = "false"),
            exchange = @Exchange(value = "users-exchange", type = "topic")
    ))
    public void deleteUserInformation(Long id) {
        reservationService.deleteReservationsByUser(id);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = "moviesDeletedQueue", durable = "false"),
            exchange = @Exchange(value = "movies-exchange", type = "topic")
    ))
    public void deleteMovieReservations(Long id) {
        reservationService.deleteReservationsByMovie(id);
    }
}
