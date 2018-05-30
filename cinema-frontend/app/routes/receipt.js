import Ember from 'ember';

const {
    inject: {
      service
    }
  } = Ember;
  
export default Ember.Route.extend({
    session: Ember.inject.service('session'),
    beforeModel(){
        if(!this.get('session.isAuthenticated'))
        this.transitionTo('showing');
    }
});
