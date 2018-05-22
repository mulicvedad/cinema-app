import Ember from 'ember';

export default Ember.Controller.extend({
    session: Ember.inject.service(),

    model: {},
    errorMessage: '',
    //openModal: false,
    actions: {
      login() {
    this.get('session').authenticate('authenticator:application', this.model, (data) => {
            console.log(data);
            Ember.set(this, 'errorMessage', '');
            Ember.set(this, 'model', {});
            console.log(this.get('session'));
            this.transitionToRoute('showing').then(function(showing) {
                showing.model.set('token',this.get('session.data.authenticated.jwt'));
            })
        })
        .catch(reason => {
            Ember.set(this, 'errorMessage', JSON.parse(reason.responseText).errorMessage);
    this.set('authenticationError', this.errorMessage || reason);
  });
},
        onCancel: function () {
          this.transitionToRoute('/');
        },
      /*  
        returnToHomePage() {
            Ember.set(this, 'openModal', null);
            this.transitionToRoute("showing"); // update
        }*/
    }
});
