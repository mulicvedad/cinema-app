package ba.unsa.etf.nwt.movieservice;

import ba.unsa.etf.nwt.movieservice.model.*;
import ba.unsa.etf.nwt.movieservice.repository.GenreRepository;
import ba.unsa.etf.nwt.movieservice.repository.MovieRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@EnableEurekaClient
@SpringBootApplication
public class MovieServiceApplication {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    public static void main(String[] args) {
        SpringApplication.run(MovieServiceApplication.class, args);
    }
//List<String> x = new ArrayList<>(Arrays.asList("xyz", "abc"));
    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            List<Genre> genres = new ArrayList<>(Arrays.asList(
                    new Genre("Comedy"),
                    new Genre("Action"),
                    new Genre("Science Fiction"),
                    new Genre("Adventure"),
                    new Genre("Fantasy"),
                    new Genre("Mystery"),
                    new Genre("Thriller")));
            for (Genre g : genres) {
                genreRepository.save(g);
            }
            MoviePerson moviePerson = new MoviePerson("Ryan Reynolds");
            MovieRole role = new MovieRole("Actor");
            moviePerson.setRoles(new HashSet<MovieRole>() {{
                add(role);
            }});

            Movie movie1 = new Movie("Deadpool 2",
                    Timestamp.valueOf("2018-05-15 10:10:10.0"),
                    "Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.",
                    "http://image.tmdb.org/t/p/w185/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                    "http://image.tmdb.org/t/p/w342/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                    "Deadpool 2",
                    8f);

            Movie movie2 = new Movie("Thor: Ragnarok",
                    Timestamp.valueOf("2017-10-25 10:10:10.0"),
                    "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.",
                    "http://image.tmdb.org/t/p/w185/kaIfm5ryEOwYg8mLbq8HkPuM1Fo.jpg",
                    "http://image.tmdb.org/t/p/w342/kaIfm5ryEOwYg8mLbq8HkPuM1Fo.jpg",
                    "Thor: Ragnarok",
                    7.6f);

            Movie movie3 = new Movie("Red Sparrow",
                    Timestamp.valueOf("2018-03-01 10:10:10.0"),
                    "Prima ballerina, Dominika Egorova faces a bleak and uncertain future after she suffers an injury that ends her career. She soon turns to Sparrow School, a secret intelligence service that trains exceptional young people to use their minds and bodies as weapons. Dominika emerges as the most dangerous Sparrow after completing the sadistic training process. As she comes to terms with her new abilities, she meets a CIA agent who tries to convince her that he is the only person she can trust.",
                    "http://image.tmdb.org/t/p/w185/uZwnaMQTdwZz1kwtrrU3IOqxnDu.jpg",
                    "http://image.tmdb.org/t/p/w342/uZwnaMQTdwZz1kwtrrU3IOqxnDu.jpg",
                    "Red Sparrow",
                    6.4f);

            Movie movie4 = new Movie("Coco",
                    Timestamp.valueOf("2017-10-27 10:10:10.0"),
                    "Despite his family’s baffling generations-old ban on music, Miguel dreams of becoming an accomplished musician like his idol, Ernesto de la Cruz. Desperate to prove his talent, Miguel finds himself in the stunning and colorful Land of the Dead following a mysterious chain of events. Along the way, he meets charming trickster Hector, and together, they set off on an extraordinary journey to unlock the real story behind Miguel's family history.",
                    "http://image.tmdb.org/t/p/w185/eKi8dIrr8voobbaGzDpe8w0PVbC.jpg",
                    "http://image.tmdb.org/t/p/w342/eKi8dIrr8voobbaGzDpe8w0PVbC.jpg",
                    "Coco",
                    7.8f);

            Movie movie5 = new Movie("The Maze Runner",
                    Timestamp.valueOf("2017-10-27 10:10:10.0"),
                    "Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow “runners” for a shot at escape.",
                    "http://image.tmdb.org/t/p/w185/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
                    "http://image.tmdb.org/t/p/w342/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
                    "The Maze Runner",
                    7f);

            Movie movie6 = new Movie("Guardians of the Galaxy",
                    Timestamp.valueOf("2017-10-27 10:10:10.0"),
                    "Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.",
                    "http://image.tmdb.org/t/p/w185/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg",
                    "http://image.tmdb.org/t/p/w342/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg",
                    "Guardians of the Galaxy",
                    7.9f);


            movie1.setGenres(new HashSet<Genre>() {{
                add(genreRepository.findByName("Action"));
                add(genreRepository.findByName("Comedy"));
                add(genreRepository.findByName("Science Fiction"));
            }});

            movie2.setGenres(new HashSet<Genre>() {{
                add(genreRepository.findByName("Action"));
                add(genreRepository.findByName("Adventure"));
                add(genreRepository.findByName("Fantasy"));
            }});
            movie3.setGenres(new HashSet<Genre>() {{
                add(genreRepository.findByName("Mystery"));
                add(genreRepository.findByName("Thriller"));
            }});
            movie4.setGenres(new HashSet<Genre>() {{
                add(genreRepository.findByName("Comedy"));
                add(genreRepository.findByName("Adventure"));
            }});
            movie5.setGenres(new HashSet<Genre>() {{
                add(genreRepository.findByName("Action"));
                add(genreRepository.findByName("Science Fiction"));
                add(genreRepository.findByName("Mystery"));
            }});
            movie6.setGenres(new HashSet<Genre>() {{
                add(genreRepository.findByName("Action"));
                add(genreRepository.findByName("Science Fiction"));
                add(genreRepository.findByName("Adventure"));
            }});

            Review review = new Review(1L, "Great movie!");

            movie1.setReviews(new HashSet<Review>() {{
                add(review);
            }});

            movie1.setMoviePeople(new HashSet<MoviePerson>() {{
                add(moviePerson);
            }});

            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
            movieRepository.save(movie4);
            movieRepository.save(movie5);
            movieRepository.save(movie6);
        };
    }
}


