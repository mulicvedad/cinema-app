import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  session: Ember.inject.service('session'),
  today: moment().format('YYYY-MM-DD'),
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
    //if(params.date){
      return Ember.RSVP.hash({
        movie: this.get('_movieService').getMovieById(params.id),
        reviews: this.get('_movieService').getReviewsByMovieId(params.id),
        showings: this.get('_cinemaService').getShowingByDateAndMovieId(this.get('today'),params.id) || {}
      });
    /* } else{
      return Ember.RSVP.hash({
        movie: this.get('_movieService').getMovieById(params.id),
        reviews: this.get('_movieService').getReviewsByMovieId(params.id),
        showings: {}
      })

    }
  */
  },

});
