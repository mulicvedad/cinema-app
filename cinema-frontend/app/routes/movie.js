import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  session: Ember.inject.service('session'),
  queryParams: {
    date: {
      refreshModel: true
    }
  },

  _movieService: service('movie-service'),
  _cinemaService: service('cinema-service'),

   model(params) {
    let token = "";
    if(this.get('session.isAuthenticated'))
      token = this.get('session.data.authenticated.jwt');
    if(params.date){
      return Ember.RSVP.hash({
        movie: this.get('_movieService').getMovieById(params.id),
        showings: this.get('_cinemaService').getShowingByDateAndMovieId(params.date,params.id) || {}
      })
    } else{
      return Ember.RSVP.hash({
        movie: this.get('_movieService').getMovieById(params.id),
        showings: {}
      })
    }
  },

});
