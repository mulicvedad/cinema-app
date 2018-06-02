import Ember from 'ember';
import SweetAlertMixin from 'ember-sweetalert/mixins/sweetalert-mixin';
const { inject: {service}} = Ember;

export default Ember.Component.extend(SweetAlertMixin, {
    movies: null,
    _cinemaService: service('cinema-service'),
    session: Ember.inject.service('session'),
    router: Ember.inject.service('-routing'),
    classNames: ['nav-bar'],
    actions: {
        logout() {

            let sweetAlert = this.get('sweetAlert');
            sweetAlert({
                title: 'Do you really want to log out',
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
        },

        updateSelection(selection) {
            Ember.getOwner(this).lookup('router:main').transitionTo('movie', selection.id);
            //this.transitionTo('movie', selection.id);
        },

        updateSearch(title) {
            this.get('_cinemaService').search(title).then(response => this.set('movies', Ember.A(response)));
        },
        clearResultsList() {
            this.set('movies', null);
        }
    }

});
