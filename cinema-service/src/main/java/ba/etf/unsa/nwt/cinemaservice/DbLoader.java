package ba.etf.unsa.nwt.cinemaservice;

import ba.etf.unsa.nwt.cinemaservice.models.*;
import ba.etf.unsa.nwt.cinemaservice.services.*;
import com.google.common.collect.Iterables;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
            addCinemaShowing(8);
       // if (reservationService.count() == 0)
       //    addReservations(10);
        if (newsService.count() == 0)
            addNews(10);

    }

    private void addNews(int num) {
        for (int i = 1; i < num; i++)
            newsService.save(new News("Vijest #" + i, "Veoma bitne vijesti", null, new Date(),
                    null));
    }
/*
    private void addReservations(int num) {
        Long numCinemaShowings = cinemaShowingService.count();
        Long numCinemaSeats = cinemaSeatService.count();
        Iterable<CinemaShowing> cinemaShowings = cinemaShowingService.all();
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
*/
    private void addCinemaShowing(int num) {
        ArrayList<String> movieTitles = new ArrayList<>(
                Arrays.asList("", "Deadpool 2", "Red Sparrow", "Coco",
                        "The Maze Runner", "Guardians of the Galaxy",
                        "Fifty Shades Freed", "Meet Me In St. Gallen"));

        ArrayList<String> moviePosterPaths = new ArrayList<>(
                Arrays.asList("", "http://image.tmdb.org/t/p/w185/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                        "http://image.tmdb.org/t/p/w185/uZwnaMQTdwZz1kwtrrU3IOqxnDu.jpg",
                        "http://image.tmdb.org/t/p/w185/eKi8dIrr8voobbaGzDpe8w0PVbC.jpg",
                        "http://image.tmdb.org/t/p/w185/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
                        "http://image.tmdb.org/t/p/w185/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg",
                        "https://ia.media-imdb.com/images/M/MV5BODI2ZmM5MzMtOWZiMC00ZGE3LTk3MWEtY2U0ZjE3ZWJlNDEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg",
                        "http://image.tmdb.org/t/p/w185/kZJEQFk6eiZ9X2x70ve6R1dczus.jpg"));

        Long numRooms = roomService.count();
        Long numTimeTable = timetableService.count();
        Long numShowingTypes = showingTypeService.count();
        for (long i = 1; i < num; i++) {
            Timetable timetable = timetableService.get((i % numTimeTable) + 1).get();
            Room room = roomService.get((i % numRooms) + 1).get();
            ShowingType showingType = showingTypeService.get((i % numShowingTypes) + 1).get();
            cinemaShowingService.save(new CinemaShowing(i, movieTitles.get((int) i), moviePosterPaths.get((int) i),
                    timetable, showingType,room, new BigDecimal(10)));
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
        int numRowsBase = 14;
        int numColsBase = 7;
        for (int i = 1; i < num; i++)
            roomService.save(new Room("S" + i, (numRowsBase) * (numColsBase), numRowsBase, numColsBase ,
                    "Hall no. " + i));
    }

    public void addSeats() {
        Iterable<Room> rooms = roomService.all();
        for (Room r : rooms) {
            for (int i = 1; i <= r.getNumRows(); i++)
                for (int j = 1; j <= r.getNumCols(); j++)
                    cinemaSeatService.save(new CinemaSeat(r, i, j, "R" + i + "-C" + j ));
        }
    }
}
