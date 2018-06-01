import Ember from 'ember';
import moment from 'moment';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Controller.extend({
  _cinemaService: service('cinema-service'),
  _swalService: service('swal-service'),
  session: Ember.inject.service(),
  today: moment().format('YYYY-MM-DD'),
  actions: {
    createShowing(movie, token) {
      if (!this.validateInputs()) {
        this.get('_swalService').error("You must fill all the fields.");
        return;
      }
      this.get('_cinemaService').addNewShowing(this.get('model.cinemaShowing'),token).then( response => {
        this.get('_swalService').success("Cinema showing successfully added",
          confirm => { this.transitionToRoute('showing'); });
      }).catch( errorResponse => {
        this.get('_swalService').error("An error occured while creating new cinema showing.\n Error details:\n" + errorResponse);
      });
    },
    setSelectedMovie(movieId) {
      this.set('model.cinemaShowing.movieId', movieId);
      let movie =  this.get('model.movies').find( m => m.id == movieId);
      this.set('model.cinemaShowing.movieTitle', movie.title);
      this.set('model.cinemaShowing.posterPath', movie.poster_path);
    },
    setSelectedRoom(roomId) {
      this.set('model.cinemaShowing.roomId', roomId);
    },
    checkAvailability() {
      if (!this.validateInputs()) {
        this.get('_swalService').error("You must fill all the fields.");
        return;
      }
      this.get('_cinemaService').checkAvailability(
        this.get('model.cinemaShowing.roomId'),
        this.get('model.cinemaShowing.startDate'),
        this.get('model.cinemaShowing.startTime'),
        this.get('model.cinemaShowing.duration')
      ).then( response => {
        this.get('_swalService').success('Requested room is available in given time interval', confirm => { });
      }).catch( error => {
        this.get('_swalService').error('Requested room is not available in given time interval');
      });
    }
  },
  validateInputs() {
    if (this.get('model.cinemaShowing.roomId') == null || this.get('model.cinemaShowing.startDate') == null
        || this.get('model.cinemaShowing.startTime') == null || this.get('model.cinemaShowing.duration') == null)
      return false;
    return true;
  }

});
