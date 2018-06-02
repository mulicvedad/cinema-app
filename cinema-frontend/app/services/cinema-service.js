import Ember from 'ember';
import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

  showing: null,

  getShowingById(id) {
    return this.ajax('GET', `/cinema/cinema-showings/${id}`);
  },

  getShowingByDate: function(date, page, size) {
    return this.ajax('GET', '/cinema/cinema-showings?date=' + date + '&page=' + page,null);
  },

  getShowingByDateAndMovieId: function(date, id) {
    return this.ajax('GET', `/cinema/cinema-showings/movie/${id}?date=` + date);
  },

  getUpcomingShowing: function(page, size) {
    return this.ajax('GET', '/cinema/cinema-showings/upcoming?page=' + page + '&size=' + size,null);
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
  search: function(title) {
    return this.ajax('GET', '/cinema/cinema-showings/search?title=' + title);
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

  addNewShowing(showing,token) {
    return this.ajax('POST', 'cinema/cinema-showings', showing,token);
  },

  getAllShowingSeats(id) {
    return this.ajax('GET', `/cinema/cinema-showings/${id}/all-seats`);
  },

  createReservation(reservation,token) {
    return this.ajax('POST', `/cinema/reservations`,reservation,token);
  },

  getAllNews() {
    return this.ajax('GET', '/cinema/news');
  }

});

