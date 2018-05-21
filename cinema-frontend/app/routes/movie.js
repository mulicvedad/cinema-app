import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({

  queryParams: {
    date: {
      refreshModel: true
    }
  },

  _movieService: service('movie-service'),
  _cinemaService: service('cinema-service'),

   model(params) {
    return Ember.RSVP.hash({
      movie: this.get('_movieService').getMovieById(params.id),
      showings: this.get('_cinemaService').getShowingByDateAndMovieId(params.date,params.id)
    });
  },

});
