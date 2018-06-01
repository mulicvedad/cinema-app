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

  getAllReservationsForUser(userId,token) {
    return this.ajax('GET', `/cinema/reservations/?userId=${userId}`,null,token);
  },

  payReservation: function(id, chargeRequest, token) {
    return this.ajax('POST', `/cinema/reservations/${id}/pay`, chargeRequest, token);
  },

  charge: function(chargeRequest) {
    return this.ajax('POST', '/payment/charge', chargeRequest);
  },

  deleteReservation(id,token) {
    return this.ajax('DELETE', `/cinema/reservations/${id}`,null,token);
  }
});
