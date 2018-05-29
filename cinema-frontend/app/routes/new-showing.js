import Ember from 'ember';
import SweetAlertMixin from 'ember-sweetalert/mixins/sweetalert-mixin';
import RSVP from 'rsvp';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend(SweetAlertMixin, {
  _cinemaService: service('cinema-service'),
  session: Ember.inject.service(),

  model() {
    return RSVP.hash({
      movies: this.get('_cinemaService').getAllMovies() || [],
      rooms: this.get('_cinemaService').getAllRooms() || [],
      cinemaShowing: this.get('_cinemaService').createShowing()
    });
  },

  beforeModel(transition) {
    if(this.get('session.isAuthenticated')) {
      this.transitionTo('new-showing');
    }
  },

  actions: {
  }
});
