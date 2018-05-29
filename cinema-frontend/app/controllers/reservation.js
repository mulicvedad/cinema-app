import Ember from 'ember';
import SweetAlertMixin from 'ember-sweetalert/mixins/sweetalert-mixin';
const {
    inject: {
      service
    }
  } = Ember;
  
export default Ember.Controller.extend(SweetAlertMixin,{
    session: Ember.inject.service(),
    router: Ember.inject.service('-routing'),
    _cinemaService: service('cinema-service'),
    seats: [],
    actions: {
        onClick(seatId) {
            let seatArray = this.get('seats');
            let flag = false;
            for(let i = 0; i < seatArray.length; i++) {
                if(seatId == seatArray[i]) {
                    seatArray.splice(i,1);
                    flag = true;
                    break;
                }
            }
            if(flag == false) {
                this.get('seats').push(seatId); 
                document.getElementById(seatId).className = 'seatSelected';    
            }
            else {
                this.set('seats',seatArray);
                document.getElementById(seatId).className = 'seat';
            }
            },
        
        seatIsReserved() {
            let sweetAlert = this.get('sweetAlert');
            sweetAlert({
                title: 'This seat is already reserved, please choose another one.',
                confirmButtonText: 'OK',
                type: 'error'
            });
        },
        reserveTicket(cId, uId, token) {
            let reservation = Ember.Object.create({
              cinemaShowingId: cId,
              userId: uId,
              seats: this.get('seats')
            });
            let sweetAlert = this.get('sweetAlert');
            sweetAlert({
                title: 'You selected the following seats: ' + this.get('seats'),
                confirmButtonText: 'Reserve seats',
                showCancelButton: true,
                cancelButtonText: 'Abort reservation',
                type: 'info'
            }).then((confirm)=>{
                this.get('_cinemaService').createReservation(reservation,token).then((response)=>{
                    let reservationId = String(response);
                    sweetAlert({
                        title: 'Successfuly reserved seats',
                        confirmButtonText: 'OK',
                        type: 'success',
                    }).then((confirm)=> {
                        let seats = [];
                        this.set('seats',[]);
                        console.log(reservationId);
                        this.get('router').transitionTo('payment',[reservationId]); // response is reservationId        
                    }),
                    function(reason){
                        sweetAlert({
                            title: "Seats couldn't be reserved. Please try again.",
                            confirmButtonText: 'OK',
                            type: 'error'
                        });
                    }                
                })
            })
        }      
    }
});
