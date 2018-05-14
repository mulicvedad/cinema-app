package ba.etf.unsa.nwt.cinemaservice;

import ba.etf.unsa.nwt.cinemaservice.models.*;
import ba.etf.unsa.nwt.cinemaservice.services.*;
import com.google.common.collect.Iterables;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

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
        if (roomService.count() == 0)
            addRooms(10);
        if (cinemaSeatService.count() == 0)
            addSeats();
        if (timetableService.count() == 0)
            populateTimetable(20);
        if (showingTypeService.count() == 0)
            addShowingTypes();
        if (reservationStatusService.count() == 0)
            addReservationStatuses();
        if (cinemaShowingService.count() == 0)
            addCinemaShowing(10);
        if (reservationService.count() == 0)
            addReservations(10);
        if (newsService.count() == 0)
            addNews(10);

    }

    private void addNews(int num) {
        for (int i = 1; i < num; i++)
            newsService.save(new News("Vijest #" + i, "Veoma bitne vijesti", null, new Date(),
                    null));
    }

    private void addReservations(int num) {
        Long numCinemaShowings = cinemaShowingService.count();
        Long numCinemaSeats = cinemaSeatService.count();
        Collection<CinemaShowing> cinemaShowings = cinemaShowingService.all();
        ReservationStatus reservationStatus;
        for (long i = 1; i < num; i++) {
            long idx = i;
            if (i % 2 == 0) reservationStatus = reservationStatusService.getStatusForNewReservation();
            else reservationStatus = reservationStatusService.getStatusForConfirmedReservation();
            for (CinemaShowing cs : cinemaShowings) {
                reservationService.save(new Reservation(cs, i, reservationStatus,
                        new ArrayList<CinemaSeat>() {
                            {
                                long seatIndex = (idx * RandomUtils.nextInt()) % cs.getRoom().getSeats().size();
                                CinemaSeat cinemaSeat = Iterables.get(cs.getRoom().getSeats(), (int)seatIndex);
                                add(cinemaSeat);
                            }
                        }
                ));
            }
        }
    }

    private void addCinemaShowing(int num) {
        Long numRooms = roomService.count();
        Long numTimeTable = timetableService.count();
        Long numShowingTypes = showingTypeService.count();
        for (long i = 1; i < num; i++) {
            Timetable timetable = timetableService.get((i % numTimeTable) + 1).get();
            Room room = roomService.get((i % numRooms) + 1).get();
            ShowingType showingType = showingTypeService.get((i % numShowingTypes) + 1).get();
            cinemaShowingService.save(new CinemaShowing(i, "Mad Max",
                    "http://image.tmdb.org/t/p/w185/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",  timetable, showingType,room));
        }
    }

    private void addReservationStatuses() {
        reservationStatusService.save(new ReservationStatus("new"));
        reservationStatusService.save(new ReservationStatus("confirmed"));
        reservationStatusService.save(new ReservationStatus("denied"));
    }

    private void addShowingTypes() {
        showingTypeService.save(new ShowingType("Regular"));
        showingTypeService.save(new ShowingType("Premiere"));
        showingTypeService.save(new ShowingType("Kids"));
        showingTypeService.save(new ShowingType("Adult"));
        showingTypeService.save(new ShowingType("3D"));
    }

    private void populateTimetable(int num) {
        Date d1 = new Date();
        Date d2;
        Calendar cl = Calendar. getInstance();
        cl.setTime(d1);
        for (int i = 0; i < num; i++) {
            if (i % 2 == 0) {
                cl.add(Calendar.HOUR, 2);
                d1 = cl.getTime();
                cl.add(Calendar.HOUR, 2);
                d2 = cl.getTime();
            } else {
                cl.add(Calendar.DAY_OF_MONTH, 1);
                d1 = cl.getTime();
                cl.add(Calendar.MINUTE, 95);
                d2 = cl.getTime();
            }

            timetableService.save(new Timetable(d1, d2));

        }
    }

    public void addRooms(int num) {
        int numRowsBase = 10;
        int numColsBase = 12;
        for (int i = 1; i < num; i++)
            roomService.save(new Room("S" + i, (numRowsBase + i) * (numColsBase + i), numRowsBase + i, numColsBase + i,
                    "Hall no. " + i));
    }

    public void addSeats() {
        Collection<Room> rooms = roomService.all();
        for (Room r : rooms) {
            for (int i = 1; i <= r.getNumRows(); i++)
                for (int j = 1; j <= r.getNumCols(); j++)
                    cinemaSeatService.save(new CinemaSeat(r, i, j, "R" + i + "-C" + j ));
        }
    }
}
