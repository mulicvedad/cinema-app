import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({

  _movieService: service('movie-service'),

   model: function (params) {
    // params.id is the movie id we passed from the showing list, we'll use it to fetch the movie details from the movie service
      console.log(params.id);
      return this.get('_movieService').getMovieById(params.id);
    }
});
