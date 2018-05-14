import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  //_userService: service('user-service'),

  model: function () {
    //return this.get('_supplierService').createSupplier();
  },

  actions: {
    onDone: function () {
     //this.get('_userService').addSupplier(this.controller.get('model'))
     // .then(()=> this.transitionTo('services'));
    },
  }
});
