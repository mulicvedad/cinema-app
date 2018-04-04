package ba.etf.unsa.nwt.cinemaservice;

import ba.etf.unsa.nwt.cinemaservice.repositories.ReservationRepository;
import ba.etf.unsa.nwt.cinemaservice.services.ReservationService;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;

@Component
public class Receiver {

    @Autowired
    ReservationService reservationService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = "usersCinemaQueue", durable = "false"),
            exchange = @Exchange(value = "users-exchange", type = "topic")
    ))
    public void deleteUserInformation(Long id) throws ServletException
    {
            reservationService.deleteReservationsByUser(id);
    }
}
