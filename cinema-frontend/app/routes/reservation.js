import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  session: Ember.inject.service('session'),
  _cinemaService: service('cinema-service'),

   model: function (params) {
     return Ember.RSVP.hash({
      showing: this.get('_cinemaService').getShowingById(params.id),
      allSeats: this.get('_cinemaService').getAllShowingSeats(params.id),
      availableSeats: this.get('_cinemaService').getAvailableSeats(params.id)
    });
   },

   beforeModel(){
     if(!this.get('session.isAuthenticated')) {
       this.transitionTo('showing');
     }
   },

});
