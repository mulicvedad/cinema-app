import Ember from 'ember';



export default Ember.Route.extend({
 // _userService: service('user-service'),
  session: Ember.inject.service(),

  beforeModel(transition) {
    if (this.get('session.isAuthenticated')) {
      var previousTransition = this.get('previousTransition');
      if (previousTransition) {
        this.set('previousTransition', null);
        previousTransition.retry();
      } else {
        this.transitionTo('application');
      }
    }
  }, 

  model: function () {
    //return this.get('_userService').createSignUpData();
  },

  actions: {
    onDone: function () {
     //this.get('_userService').addSupplier(this.controller.get('model'))
     // .then(()=> this.transitionTo('services'));
    },
  }
});
