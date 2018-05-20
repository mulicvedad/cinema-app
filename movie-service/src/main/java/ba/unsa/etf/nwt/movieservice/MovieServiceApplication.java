package ba.unsa.etf.nwt.movieservice;

import ba.unsa.etf.nwt.movieservice.model.*;
import ba.unsa.etf.nwt.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.HashSet;

@EnableEurekaClient
@SpringBootApplication
public class MovieServiceApplication {
    @Autowired
    private MovieRepository movieRepository;

    public static void main(String[] args) {
        SpringApplication.run(MovieServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            Genre genre1 = new Genre("Comedy");
            Genre genre2 = new Genre("Action");
            Genre genre3 = new Genre("Science Fiction");

            MoviePerson moviePerson = new MoviePerson("Ryan Reynolds");
            MovieRole role = new MovieRole("Actor");
            moviePerson.setRoles(new HashSet<MovieRole>() {{
                add(role);
            }});

            Movie movie = new Movie("Deadpool 2",
                    Timestamp.valueOf("2018-05-15 10:10:10.0"),
                    "Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.",
                    "http://image.tmdb.org/t/p/w185/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                    "http://image.tmdb.org/t/p/w342/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
                    "Deadpool 2",
                    8f);

            movie.setGenres(new HashSet<Genre>() {{
                add(genre1);
                add(genre2);
                add(genre3);
            }});

            Review review = new Review(1L, "Great movie!");

            movie.setReviews(new HashSet<Review>() {{
                add(review);
            }});

            movie.setMoviePeople(new HashSet<MoviePerson>() {{
                add(moviePerson);
            }});

            movieRepository.save(movie);
        };
    }
}


