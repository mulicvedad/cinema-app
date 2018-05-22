import Ember from 'ember';
import SweetAlertMixin from 'ember-sweetalert/mixins/sweetalert-mixin';
const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend(SweetAlertMixin, {
  _userService: service('user-service'),

  model: function () {
    return this.get('_userService').createUser();
  },

  actions: {
    onNext: function () {
      let sweetAlert = this.get('sweetAlert');
      this.get('_userService').registerUser(this.controller.get('model'))
     .then(()=>{
        sweetAlert({
          title: 'Registration successful',
          confirmButtonText: 'OK',
          confirmButtonColor: '#DC5154',
          type: 'success'
        })
        .then((confirm)=> {
          this.transitionTo('showing');}
        );
     }, 
     function(reason) {
       let errorMessage = reason.responseJSON.error.message;
      sweetAlert({
        title: errorMessage,
        confirmButtonText: 'Try again',
        confirmButtonColor: '#DC5154',
        type: 'error'
    })
     })
    },
  }
});
