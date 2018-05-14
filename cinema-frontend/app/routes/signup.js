import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  _userService: service('user-service'),

  model: function () {
    return this.get('_userService').createUser();
  },

  actions: {
    onDone: function () {
     this.get('_userService').registerUser(this.controller.get('model'))
     .then(()=> this.transitionTo('showing'));
    },
  }
});
