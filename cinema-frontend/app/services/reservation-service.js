import Ember from 'ember';
import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

  currentChargeRequest: null,

  createChargeRequest() {
    let newChargeRequest = Ember.Object.create({
      description: '',
      amount: '',
      currency: 'EUR',
      stripeEmail: 'stripe@gmail.com',
      stripeToken:'',
    });
    this.set('currentChargeRequest', newChargeRequest);
    return this.get('currentChargeRequest');
  },

  getReservationById: function(id) {
    return this.ajax('GET', `/cinema/reservations/${id}`);
  },

  payReservation: function(id, chargeRequest, token) {
    console.log('usao u rezervaciju');
    console.log(id);
    console.log(chargeRequest);
    return this.ajax('POST', `/cinema/reservations/${id}/pay`, chargeRequest, token);
  },

  charge: function(chargeRequest) {
    return this.ajax('POST', '/payment/charge', chargeRequest);
  }
});
