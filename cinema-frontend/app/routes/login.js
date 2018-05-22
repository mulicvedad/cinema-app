import Ember from 'ember';


export default Ember.Route.extend({
  session: Ember.inject.service(),

  beforeModel(transition) {
    if (this.get('session.isAuthenticated')) {
      var previousTransition = this.get('previousTransition');
      if (previousTransition) {
        this.set('previousTransition', null);
        previousTransition.retry();
      } else {
        this.transitionTo('/');
      }
    }
  }
  
});
