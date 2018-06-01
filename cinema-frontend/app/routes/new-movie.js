import Ember from 'ember';
import SweetAlertMixin from 'ember-sweetalert/mixins/sweetalert-mixin';

const { inject: {service}} = Ember;

export default Ember.Route.extend(SweetAlertMixin, {
    _movieService: service("movie-service"),
    session: Ember.inject.service(),

    model(){
        return Ember.RSVP.hash({
            genres: this.get('_movieService').getAllGenres(), 
            moviesTMDB: this.get('_movieService').getMostPopularMovies(),
            movie: this.get('_movieService').createMovie()
          });
    },

    beforeModel() {
        if(!this.get('session.isAuthenticated') || 
        this.get('session.data.authenticated.user.roles') != 'ROLE_ADMIN') {
          this.transitionTo('showing');
        }
      },    
    
});
