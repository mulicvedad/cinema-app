package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.CinemaShowingController;
import ba.etf.unsa.nwt.cinemaservice.controllers.dto.CinemaShowingDTO;
import ba.etf.unsa.nwt.cinemaservice.controllers.dto.MovieShowingDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.*;
import ba.etf.unsa.nwt.cinemaservice.repositories.CinemaSeatRepository;
import ba.etf.unsa.nwt.cinemaservice.repositories.CinemaShowingRepository;
import ba.etf.unsa.nwt.cinemaservice.utils.ReportHelper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class CinemaShowingService extends BaseService<CinemaShowing, CinemaShowingRepository> {

    @Autowired
    RoomService roomService;

    @Autowired
    ShowingTypeService showingTypeService;

    @Autowired
    CinemaSeatRepository cinemaSeatRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    @Autowired
    private TimetableService timetableService;

    static final long ONE_MINUTE_IN_MILLIS=60000;
    private final String REPORT_FILENAME = "upcoming_showings";
    private final String REPORT_FILE_EXTENSION = ".pdf";
    private final String REPORT_TITLE = "UPCOMING SHOWINGS";
    private final String REPORT_CREATOR = "LEVA";
    private final Integer REPORT_TABLE_NUM_COLUMNS = 5;

    public Page<CinemaShowing> findUpcomingShowings(Pageable pageable) {
        return repo.findUpcoming(pageable);
    }

    public Page<CinemaShowing> findByDate(Date date, Pageable pageable) {
        return repo.findAllByDate(date, pageable);
    }

    public Collection<CinemaShowing> findByDateAndMovie(Date date, Long movieId) {
        return repo.findByDateAndMovieId(date, movieId);
    }

    public void createCinemaShowing(CinemaShowingDTO cinemaShowingDTO) throws ParseException {

        String url;
        try {
            Application application = eurekaClient.getApplication("movie-service");
            InstanceInfo instanceInfo = application.getInstances().get(0);
            url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "movies/" + cinemaShowingDTO.movieId;
            Logger.getLogger(CinemaShowingController.class.toString()).info("URL " + url);
        } catch (Exception e) {
            throw new ServiceException("Movie service is not available");
        }
        try {
            restTemplate.getForEntity(url, Map.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new ServiceException("Movie with given id doesn't exist");
            else
                throw e;
        }

        Date startDateTime;
        startDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(cinemaShowingDTO.startDate
                + " " + cinemaShowingDTO.startTime);
        long timeMIllis= startDateTime.getTime();
        Date endDateTime = new Date(timeMIllis + (cinemaShowingDTO.duration * ONE_MINUTE_IN_MILLIS));

        if (startDateTime.after(endDateTime) || startDateTime.before(new Date()))
            throw new ServiceException("Invalid dates and/or times");

        Optional<Room> room = roomService.get(cinemaShowingDTO.roomId);
        if (!room.isPresent())
            throw new ServiceException("Requested room doesn't exist");

        int numOverlapping = repo.numberOfOverlappingShowings(room.get(), startDateTime, endDateTime);
        if (numOverlapping > 0)
            throw new ServiceException("Cannot create cinema showing because requsted cinema room is taken in requested" +
                    "time interval");

        Timetable cinemaShowingTimeTable = new Timetable(startDateTime, endDateTime);
        timetableService.save(cinemaShowingTimeTable);
        repo.save(new CinemaShowing(cinemaShowingDTO.movieId, cinemaShowingDTO.movieTitle, cinemaShowingDTO.posterPath,
                cinemaShowingTimeTable, null, room.get(), new BigDecimal(5)));
    }

    public Collection<CinemaSeat> getAvailableSeats(Long id) {
        Optional<CinemaShowing> cinemaShowing = repo.findById(id);
        Collection<CinemaSeat> reservedSeats = cinemaSeatRepository.findReservedSeats(id);
        Collection<CinemaSeat> allSeats = cinemaSeatRepository.findAllByRoom(cinemaShowing.get().getRoom());
        Collection<CinemaSeat> availableSeats = new ArrayList<>();
        for (CinemaSeat cinemaSeat : allSeats) {
            if (!reservedSeats.contains(cinemaSeat))
                availableSeats.add(cinemaSeat);
        }
        return availableSeats;
    }

    public boolean checkAvailability(Long roomId, String date, String time, Integer duration) throws ParseException{
        Date startDateTime;
        startDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(date + " " + time);
        long timeMIllis= startDateTime.getTime();
        Date endDateTime = new Date(timeMIllis + (duration * ONE_MINUTE_IN_MILLIS));
        Optional<Room> room = roomService.get(roomId);
        if (!room.isPresent())
            throw new ServiceException("Room with id " + roomId + " doesn't exist");
        int numOverlapping = repo.numberOfOverlappingShowings(room.get(), startDateTime, endDateTime);
        return numOverlapping == 0;
    }

    public Collection<CinemaSeat> getAllShowingSeats(Long id) {
        Optional<CinemaShowing> cinemaShowing = repo.findById(id);
        Collection<CinemaSeat> allSeats = cinemaSeatRepository.findAllByRoom(cinemaShowing.get().getRoom());
        return allSeats;
    }

    public CinemaShowing getCinemaShowing(Long id) {
        return repo.findCinemaShowingById(id);
    }

    public String generateReport() {
        try {
            String reportFilename = REPORT_FILENAME + Math.random() + REPORT_FILE_EXTENSION;
            OutputStream file = new FileOutputStream(new File(reportFilename));
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, file);
            document.open();
            ReportHelper.initializeDocument(document, REPORT_TITLE, REPORT_CREATOR);
            ReportHelper.addGeneralInfo(document, REPORT_TITLE);
            ReportHelper.addEmptyRow(document);
            addCinemaShowingsTable(document);
            document.close();
            file.close();
            return reportFilename;
        } catch (Exception e) {
            return null;
        }
    }

    private void addCinemaShowingsTable(Document document) throws DocumentException {
        Collection<CinemaShowing> cinemaShowings = repo.findAll();
        PdfPTable pdfTable = new PdfPTable(REPORT_TABLE_NUM_COLUMNS);
        ReportHelper.setTableHeaders(pdfTable, Arrays.asList("Date", "Day", "Time", "Movie", "Hall"));
        for (CinemaShowing cs : cinemaShowings) {
            Date showingDateTime = cs.getTimetable().getStartDateTime();
            String formattedDate = getFormattedDate(showingDateTime);
            String formattedTime = getFormattedTime(showingDateTime);
            String day = getDayFromDate(showingDateTime);
            String movieTitle = cs.getMovieTitle();
            String roomName = cs.getRoom().getTitle();
            ReportHelper.addRowToTable(pdfTable, Arrays.asList(formattedDate, day, formattedTime, movieTitle, roomName));
        }
        document.add(pdfTable);
    }

    private String getFormattedDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        return dateFormat.format(date);
    }

    private String getFormattedTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        return dateFormat.format(date);
    }

    private String getDayFromDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEEE");
        return dateFormat.format(date);
    }

    public Page<CinemaShowing> getAllShowingMoviesForDate(Date date, Pageable pageable) {
        Page<CinemaShowing> showings = repo.findAllByDate(date, pageable);
        List<CinemaShowing> cinemaShowings = showings.getContent();
        List<CinemaShowing> newCinemaShowings = new ArrayList<>();
        for (int i = 0; i < cinemaShowings.size(); i++) {
            if (!containsMovieId(newCinemaShowings, cinemaShowings.get(i).getMovieId())) {
                CinemaShowing csTemp = cinemaShowings.get(i);
                csTemp.setFormattedShowings(getFormattedShowingTimes(date, csTemp.getMovieId()));
                newCinemaShowings.add(csTemp);
            }
        }
        showings = new PageImpl<>(newCinemaShowings);
        return showings;
    }

    private String getFormattedShowingTimes(Date date, Long movieId) {
        List<CinemaShowing> showings = repo.findAllByDateAndMovieId(date, movieId);
        String showingTimes = "";
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        for (int i = 0; i < showings.size(); i++) {
            showingTimes += dateFormat.format(showings.get(i).getTimetable().getStartDateTime());
            if (i != showings.size() - 1)
                showingTimes += ", ";
        }
        return showingTimes;
    }

    public List<CinemaShowing> getMoviesByTitleLike(String movieTitle) {
        List<CinemaShowing> cinemaShowings = repo.getMoviesByTitleLike(movieTitle);
        List<CinemaShowing> newCinemaShowings = new ArrayList<>();
        for (int i = 0; i < cinemaShowings.size(); i++) {
            if (!containsMovieId(newCinemaShowings, cinemaShowings.get(i).getMovieId())) {
                newCinemaShowings.add(cinemaShowings.get(i));
            }
        }
        return newCinemaShowings;
    }

    private boolean containsMovieId(List<CinemaShowing> cinemaShowings, Long movieId) {
        for (CinemaShowing cs : cinemaShowings)
            if (cs.getMovieId() == movieId)
                return true;
        return false;
    }

}
