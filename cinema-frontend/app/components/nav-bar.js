import Ember from 'ember';
import SweetAlertMixin from 'ember-sweetalert/mixins/sweetalert-mixin';

export default Ember.Component.extend(SweetAlertMixin, {
    session: Ember.inject.service('session'),
    router: Ember.inject.service('-routing'),
    classNames: ['nav-bar'],
    actions: {
        logout() {

            let sweetAlert = this.get('sweetAlert');
            sweetAlert({
                title: 'Are you sure you want to log out',
                confirmButtonText: 'Yes',
                showCancelButton: true,
                cancelButtonText: 'No',
                confirmButtonColor: '#DC5154',
                type: 'warning'
            }).then((confirm)=>{
                sweetAlert({
                    title: 'Successfuly logged out',
                    confirmButtonText: 'OK',
                    confirmButtonColor: '#DC5154',
                    type: 'success'
                }).then((conifrm)=>{
                    this.get('session').invalidate();
                    this.get('router').transitionTo('showing');
                })
            })
        },
        updateEmail() {
            let sweetalert = this.get('sweetAlert');
            sweetalert({

            })
        }
    }

});
