import Ember from 'ember';

export default Ember.Route.extend({
   model: function (params) {
   	console.log(params);
   },

  actions: {
    proceedToCheckout: function () {
      this.transitionTo('payment');
    },
  }

});
