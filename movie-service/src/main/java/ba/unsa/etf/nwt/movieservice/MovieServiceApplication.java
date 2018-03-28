package ba.unsa.etf.nwt.movieservice;

import ba.unsa.etf.nwt.movieservice.model.*;
import ba.unsa.etf.nwt.movieservice.repository.GenreRepository;
import ba.unsa.etf.nwt.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.HashSet;

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
			Genre genre1 = new Genre("Drama");
            MoviePerson moviePerson = new MoviePerson("Greta", "Gerwig");
            MovieRole role = new MovieRole("Director");
            moviePerson.setRoles(new HashSet<MovieRole>(){{
            	add(role);
			}});

			Movie movie = new Movie("Lady Bird",
					Timestamp.valueOf("2017-11-03 10:10:10.0"),
					"In 2002, an artistically inclined seventeen-year-old girl comes of age in Sacramento, California.",
					"/kqjL17yufvn9OVLyXYpvtyrFfak.jpg");

			movie.setGenres(new HashSet<Genre>(){{
				add(genre1);
			}});

            Review review = new Review(1L, "Great movie!");

			movie.setReviews(new HashSet<Review>(){{
				add(review);
			}});

			movie.setMoviePeople(new HashSet<MoviePerson>(){{
				add(moviePerson);
			}});

            movieRepository.save(movie);
		};
	}
}


