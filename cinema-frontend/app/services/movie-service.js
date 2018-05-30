import Ember from 'ember';
import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({
	getAllGenres: function() {
		return this.ajax('GET', '/movie/movies/genres');
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

	createNewMovie: function(movie) {
		return this.ajax('POST', '/movie/movies/new', movie);
	},
	createReview(review, token) {
		return this.ajax('POST', '/movie/review/', review, token);
	}
});
