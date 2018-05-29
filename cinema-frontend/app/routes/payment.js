import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  _reservationService: service('reservation-service'),
  session: Ember.inject.service('session'),


  model(params) {
    return this.get('_reservationService').createChargeRequest();
  },

  setupController(controller, model,transition) {
    // Call _super for default behavior
    this._super(controller, model);
    // Implement your custom setup after
    controller.set('reservationId',transition.params.payment.id);
  }

});
