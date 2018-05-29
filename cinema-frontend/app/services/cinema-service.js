import Ember from 'ember';
import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

  showing: null,

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

  createShowing() {
    let newShowing = Ember.Object.create({
      roomId: null,
      movieId: null,
      movieTitle: null,
      posterPath: null,
      startDate: null,
      startTime: null,
      duration: null
    });
    this.set('showing', newShowing);
    return this.get('showing');
  },

  getAllMovies() {
    return this.ajax('GET', '/movie/movies/all');
  },

  getAllRooms() {
    return this.ajax('GET', '/cinema/rooms');
  },

  checkAvailability(roomId, startDate, startTime, duration) {
    return this.ajax('GET', '/cinema/cinema-showings/check-availability?'
      + "room=" + roomId
      + "&date=" + startDate
      + "&time=" + startTime
      + "&duration=" + duration
    );
  },

  addNewShowing(showing) {
    return this.ajax('POST', 'cinema/cinema-showings', showing);
  }
});

