import Ember from 'ember';

const { inject: {service}} = Ember;

export default Ember.Route.extend({
    session: Ember.inject.service('session'),
    _reservationService: service('reservation-service'),

    model(params) {
        return Ember.RSVP.hash({
            reservations: this.get('_reservationService').getAllReservationsForUser(params.id,this.get('session.data.authenticated.jwt')),
        })
    },

    beforeModel() {
        if(!this.get('session.isAuthenticated'))
            this.transitionTo('showing');
    }
});
