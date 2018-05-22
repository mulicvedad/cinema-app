import Ember from 'ember';

export default Ember.Component.extend({
    session: Ember.inject.service('session'),
    classNames: ['nav-bar'],
   // active:false,
    actions: {
        logout() {
            this.get('session').invalidate();
            Ember.set(this,'active',false);
            this.transitionToRoute("/");
        },
      //  activateModal() {
      //      Ember.set(this,'active',true);
      //  },
      //  hideModal() {
      //      Ember.set(this, 'active',false);
      //  }
    }
});