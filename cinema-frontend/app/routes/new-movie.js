import Ember from 'ember';

const { inject: {service}} = Ember;

export default Ember.Route.extend({
    _movieService: service("movie-service"),
    model(){
        return Ember.RSVP.hash({
            genres: this.get('_movieService').getAllGenres(), // for now, only genres
          });
          }
});
