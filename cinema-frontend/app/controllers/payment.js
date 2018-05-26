import Ember from 'ember';
const {
  Controller, get, inject: { service }, set } = Ember;

export default Controller.extend({

  stripev3: service(),
  _reservationService: service('reservation-service'),
  reservationId: null,


  options: {
    hidePostalCode: true,
    style: {
      base: {
        color: '#fff'
      }
    }
  },

  token: null,

  actions: {
   submit(stripeElement) {
      let stripe = get(this, 'stripev3');
      stripe.createToken(stripeElement).then(({token}) => {
        set(this, 'token', token);
        set(this,'model.stripeToken',token.id);
        set(this,'model.description', 'Cinema Ticket');
        set(this,'model.amount', '100');
        this.get('_reservationService').payReservation(this.get('reservationId'), this.get('model'));
      });
    }
  }
});