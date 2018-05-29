import Ember from 'ember';
import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

	getShowingById(id) {
		return this.ajax('GET', `/cinema/cinema-showings/${id}`);
	},
	getShowingByDate: function(date) {
		return this.ajax('GET', '/cinema/cinema-showings?date=' + date,null);
	},

	getShowingByDateAndMovieId: function(date, id) {
		return this.ajax('GET', `/cinema/cinema-showings/movie/${id}?date=` + date);
	},

	getUpcomingShowing: function() {
		return this.ajax('GET', '/cinema/cinema-showings/upcoming',null);
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
	},

	getAllShowingSeats(id) {
		return this.ajax('GET', `/cinema/cinema-showings/${id}/all-seats`);
	},

	createReservation(reservation,token) {
		return this.ajax('POST', `/cinema/reservations`,reservation,token);
	}
	
});

