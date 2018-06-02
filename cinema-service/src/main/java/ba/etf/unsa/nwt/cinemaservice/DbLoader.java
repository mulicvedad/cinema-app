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
        if (newsService.count() == 0)
            addNews();
    }

    private void addNews() {
        newsService.save(new News("Deadpool 2’ Tops $500 Million at Worldwide Box Office", "Fox’s “Deadpool 2” has topped $500 million at the worldwide box office in less than two weeks.\n" +
                "\n" +
                "“Deadpool 2” has taken in $218.5 million domestically and another $287 million in international markets, led by $28 million in the U.K. and $27 million in South Korea.\n" +
                "\n" +
                "The original “Deadpool” stunned the industry two years ago with a $132.4 million debut weekend, which holds the record for an R-rated title, and went on to finish with $330 million domestically and $420 million internationally. Fox decided in January to open the Ryan Reynolds sequel two weeks earlier to get it into theaters a week ahead of “Solo: A Star Wars Story.”", "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/media/image/2018/02/shatterstar-terry-crews-trailer-deadpool-2.jpg", new Date(),
                null));
        newsService.save(new News("Where was Red Sparrow filmed?", "Most of Red Sparrow was filmed in Budapest and Hungary, but also Bratislava, Vienna and London appear in the movie. Here are some of the most relevant spoiler-free filming locations for this thriller, based on the 2013 book by Jason Matthews" +
                "The opening sequence was filmed at the magnificent Hungarian State Opera House in Budapest. The secret intelligence service Sparrow School was set at the 19th century Festetics Palace in Dég.\n" +
                "Some interiors were shot in another Festetics Palace, this one located in the city of Keszthely, west of Lake Balaton." +
                "The crew was also filming in london, around Whitehall Place in Westminster and at Heathrow Terminal 2.\n", "https://atom-tickets-res.cloudinary.com/image/upload/c_fill,g_north,h_425,q_auto,w_992/v1515438125/ingestion-images-archive-prod/archive/1515438124628_233269_cops_10.jpg", new Date(),
                null));
        newsService.save(new News("To 3D Or Not To 3D: Buy The Right Guardians of the Galaxy Ticket\n", "Guardians of the Galaxy Vol. 2, much like most films on the market, is a post-conversion 3D effort. However, just as he worked on Guardians of the Galaxy's third dimensional enhancements, James Gunn himself oversaw the 3D AND IMAX conversion of this film. While you can tell a good 3D job had an excellent team behind its creation, there's some extra special love in this film's presentation, both through ratio expansion and 3D thrills. Your eyes are going to be so busy with this one!" +
                "The depth in Guardians of the Galaxy Vol. 2's \"Beyond The Window\" is quite astounding, especially considering the elements that are on display in the world of the film's visuals. In particular, the sequences exploring space travel show this aspect of the film's presentation very well. Not to mention, battle scenes where there's tons of crafts zooming around in every direction are enhanced by the field of depth between said craft and the backgrounds they're fighting against. Even dangling a Guardian out of an attack craft demonstrates perfect visual focus, paying more attention to the person in peril than the backgrounds, but not losing any detail." +
                "There is a fair amount of visual confusion during the earlier parts of Guardians of the Galaxy Vol. 2. The frenetic action, and some of the quick pan motions, wonk the eye out just a little. This causes the focus of the frame to be a bit blurred, and taxes the eyes when trying to understand just what's going on. Of course, due to the dimmed brightness of this particular presentation, the entire film is a little rough to watch when it comes to eye strain, as there are moments where taking off your classes is greatly encouraged, if only to take a break.", "https://static.rogerebert.com/uploads/review/primary_image/reviews/guardians-of-the-galaxy-vol-2-2017/hero_GOT2-2017.jpg", new Date(),
                null));
        newsService.save(new News("The Maze Runner series makes an exit with the proficient, energetic sci-fi junk of The Death Cure", "As it turns out, with hardly anyone outside of hardcore Maze Runner fans (and however many supplemental moviegoers it takes to get within range of $100 million domestic) paying attention, the runty little brother of The Hunger Games has gotten surprisingly proficient in that area of well-produced sci-fi junk where a lot of the dialogue consists of variations on, “Go, go, go!” Maze Runner: The Death Cure—being the continuing adventures of Thomas (Dylan O’Brien) and a ragtag group of young adults who have been subjected to sinister experiments involving their immunity to a world-ravaging zombie plague—caps the trilogy in peak form. For this series, that means it’s watchable, often surprisingly fun, and largely devoid of personality.\n" +
                "\n" +
                "In the first film, Thomas, along with Newt (Thomas Brodie-Sangster), Teresa (Kaya Scodelario), Minho (Ki Hong Lee) and some nondescript others, were trapped in a walled compound outfitted with a perilous maze; in the sequel, The Scorch Trials, they broke out and rebelled against WCKD, the organization that held them prisoner in search of a self-serving plague vaccine. The Death Cure picks up about half a year later, with Thomas leading some of his old friends on a rescue mission to retrieve their captured buddy Minho—and, he secretly hopes, Teresa, who has betrayed her friends and joined up with WCKD’s Ava Paige (Patricia Clarkson) to pursue further research. Without Teresa around, Scorch Trials newbie Brenda (Rosa Salazar) has officially been promoted to Only Girl duties.", "http://d13ezvd6yrslxm.cloudfront.net/wp/wp-content/images/maze-runner-the-death-cure-clips.png", new Date(),
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
                Arrays.asList("", "http://image.tmdb.org/t/p/w342/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                        "http://image.tmdb.org/t/p/w342/uZwnaMQTdwZz1kwtrrU3IOqxnDu.jpg",
                        "http://image.tmdb.org/t/p/w342/eKi8dIrr8voobbaGzDpe8w0PVbC.jpg",
                        "http://image.tmdb.org/t/p/w342/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
                        "http://image.tmdb.org/t/p/w342/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg",
                        "http://image.tmdb.org/t/p/w342/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg",
                        "http://image.tmdb.org/t/p/w342/kZJEQFk6eiZ9X2x70ve6R1dczus.jpg"));

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
