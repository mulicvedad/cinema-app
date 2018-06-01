import Ember from 'ember';

const { inject: { service } } = Ember;

export default Ember.Controller.extend({
    session: Ember.inject.service(),
    _reservationService: service('reservation-service'),
    _swalService: service('swal-service'),
    router: Ember.inject.service('-routing'),

    actions: {
        payTickets(reservationId) {
            this.get('router').transitionTo('payment',[reservationId]); // response is reservationId   
        },
        deleteReservation(reservationId,token) {
            console.log(token);
            sweetAlert({
                title: 'Are you sure you want to delete this reservation?',
                showCancelButton: true,
                type: 'warning',
                confirmButtonText: 'Yes',
                cancelButtonText: 'No',
                confirmButtonColor: '#DC5154',
            }).then((confirm)=>{
                this.get('_reservationService').deleteReservation(reservationId,token).then( response => {
                    this.get('_swalService').success("Reservation successfully removed",
                      confirm => { this.get('target.router').refresh(); });
                  }).catch( errorResponse => {
                    this.get('_swalService').error("An error occured while deleting reservation.\n Error details:\n" + errorResponse);
                  });        
    
            })
        }
    }
});
