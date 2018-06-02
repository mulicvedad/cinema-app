import Ember from 'ember';
import SweetAlertMixin from 'ember-sweetalert/mixins/sweetalert-mixin';

export default Ember.Controller.extend(SweetAlertMixin,{
    session: Ember.inject.service(),

    model: {},
    errorMessage: '',
    actions: {
      login() {
        let sweetAlert = this.get('sweetAlert');
        this.get('session').authenticate('authenticator:application', this.model, (data) => {
                Ember.set(this, 'errorMessage', '');
                Ember.set(this, 'model', {});
                sweetAlert({
                    title: 'Successful login',
                    confirmButtonText: 'OK',
                    confirmButtonColor: '#DC5154',
                    type: 'success'
                }).then((confirm)=>{
                    this.transitionToRoute('showing');
                })
            })
            .catch(reason => {
                Ember.set(this, 'errorMessage', JSON.parse(reason.responseText).errorMessage);
                this.set('authenticationError', this.errorMessage || reason);
                sweetAlert({
                    title: 'Wrong username or password',
                    confirmButtonText: 'Try again',
                    confirmButtonColor: '#DC5154',
                    type: 'error'
                })
            });
        },
        onCancel: function () {
          this.transitionToRoute('/');
        },
    }
});
