import Ember from 'ember';
const {
  Controller, get, inject: { service }, set } = Ember;

export default Controller.extend({

  stripev3: service(),

  options: {
    hidePostalCode: true,
    style: {
      base: {
        color: '#333'
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
        console.log(this.get('model'));
      });
    }
  }
});