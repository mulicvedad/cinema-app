import Ember from 'ember';
import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

	getShowingByDate: function(date) {
		return this.ajax('GET', '/cinema/cinema-showings?date=' + date);
	},

	getUpcomingShowing: function() {
		return this.ajax('GET', '/cinema/cinema-showings/upcoming');
	},

	getAvailableSeats: function(id) {
		return this.ajax('GET', `/cinema/cinema-showings/${id}/available-seats`);
	},

	getCinemaSeat: function(id) {
		return this.ajax('GET', `/cinema/cinema-seats/${id}`);
	},

	createCinemaSeat: function(cinemaSeat) {
		return this.ajax('POST', '/cinema/cinema-seats', cinemaSeat);
	},

	deleteCinemaSeat: function(id) {
		return this.ajax('DELETE', `/cinema/cinema-seats/${id}`);
	},

	getShowingByDateAndMovieId: function(date, id) {
		return this.ajax('GET', '/cinema/cinema-showings/movie/' + id + '?date=' + date);
	}
});

