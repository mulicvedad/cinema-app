import Ember from 'ember';
import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

  movie: null,
	movieReqObj: null,

	createMovie() {
		let newMovie = Ember.Object.create({
		title: null,
		overview: null,
		largePosterPath: null,
		tmdbId: null,
		genres: null,
		reviews: null,
		moviePeople: null,
		release_date: null,
		poster_path: null,
		original_title: null,
		vote_average: null
		});
		this.set('movie', newMovie);
		return this.get('movie');
	},

	getAllGenres: function() {
		return this.ajax('GET', '/movie/genres');
	},

	getMostPopularMovies: function() {
		return this.ajax('GET', '/movie/movies/tmdb/popular');
	},

	getMovieByTmdbId: function(id) {
		return this.ajax('GET', `/movie/movies/tmdb/${id}`);
	},

	getMovieByTitle: function(title) {
		return this.ajax('GET', '/movie/movies/find/title?title' + title);
	},

	getMovieById: function(id) {
		return this.ajax('GET', `/movie/movies/${id}`);
	},

	deleteMovie: function(id) {
		return this.ajax('DELETE', `/movie/movies/${id}`);
	},

	getMovieCast: function(id) {
		return this.ajax('GET', `/movie/movies/tmdb/${id}/credits`);
	},

	getReviewsByMovieId: function(id) {
		return this.ajax('GET', `/movie/review/${id}`);
	},

	getReviewsByUserId: function(id) {
		return this.ajax('GET', `/movie/review/user/${id}`);
	},

	createNewMovie: function(movie, token) {
		return this.ajax('POST', '/movie/movies/new', movie, token);
  },

	createReview(review, token) {
		return this.ajax('POST', '/movie/review/', review, token);
  },

  createMovieRequestObj() {
    let newMovie = Ember.Object.create({
      movie: {
        title: null,
        releaseDate: null,
        overview: null,
        posterPath: null,
        largePosterPath: null
      },
      genres: []
    });
    this.set('movieReqObj', newMovie);
    return this.get('movieReqObj');
  }
});
