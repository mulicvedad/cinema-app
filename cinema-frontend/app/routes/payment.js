import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  _reservationService: service('reservation-service'),


  model(params) {
    return this.get('_reservationService').createChargeRequest()
  },

});
