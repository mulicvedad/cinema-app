package ba.etf.unsa.nwt.cinemaservice;

import ba.etf.unsa.nwt.cinemaservice.models.*;
import ba.etf.unsa.nwt.cinemaservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class DbLoader implements CommandLineRunner {
    @Autowired
    private CinemaSeatService cinemaSeatService;
    @Autowired
    private CinemaShowingService cinemaShowingService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ShowingTypeService showingTypeService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private ReservationStatusService reservationStatusService;

    @Override
    public void run(String... args) throws Exception {
        int num = 10;
        if (roomService.count() == 0)
            addRooms(num);
        if (cinemaSeatService.count() == 0)
            addSeats(num);
        if (timetableService.count() == 0)
            populateTimetable(num);
        if (showingTypeService.count() == 0)
            addShowingTypes(num);
        if (reservationStatusService.count() == 0)
            addReservationStatuses(num);
        if (cinemaShowingService.count() == 0)
            addCinemaShowing(num);
        if (reservationService.count() == 0)
            addReservations(num);
        if (newsService.count() == 0)
            addNews(num);
    }

    private void addNews(int num) {
        for (int i = 1; i < num; i++)
            newsService.save(new News("Vijest #" + i, "Veoma bitne vijesti", null, new Date(),
                    null));
    }

    private void addReservations(int num) {
        for (long i = 1; i < num; i++) {
            final long idx = i;
            reservationService.save(new Reservation(cinemaShowingService.get(i).get(), i, reservationStatusService.get(idx).get(),
                    new ArrayList<CinemaSeat>(){
                        {
                            add(cinemaSeatService.get(idx).get());
                        }
                    }
            ));
        }
    }

    private void addCinemaShowing(int num) {
        for (long i = 1; i < num; i++)
            cinemaShowingService.save(new CinemaShowing(i, timetableService.get(i).get(), showingTypeService.get(i).get(),
                    roomService.get(i).get()));
    }

    private void addReservationStatuses(int num) {
        for (int i = 1; i < num; i++)
            reservationStatusService.save(new ReservationStatus("Status #" + i));
    }

    private void addShowingTypes(int num) {
        for (int i = 1; i < num; i++)
            showingTypeService.save(new ShowingType("Type no. " + i));
    }

    private void populateTimetable(int num) {
        for (int i = 1; i < num; i++)
            timetableService.save(new Timetable(new Date(), new Date()));
    }

    public void addRooms(int num) {
        for (int i = 1; i < num; i++)
            roomService.save(new Room("S" + i, 50 + i * 10, "Hall no. " + i));
    }

    public void addSeats(int num) {
        for (int i = 1; i < num; i++) {
            cinemaSeatService.save(new CinemaSeat(roomService.get((long)i).get(),1 + i, 3 + i, "X" + i ));
        }
    }
}
