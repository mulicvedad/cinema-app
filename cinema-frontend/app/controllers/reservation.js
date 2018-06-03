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
                confirmButtonColor: '#DC5154',
                type: 'error'
            });
        },
        reserveTicket(cId, uId, token) {

            let sweetAlert = this.get('sweetAlert');
            console.log(token);
            if(!token) {
                sweetAlert({ 
                    title: 'Login required',
                    text: 'Only logged in users can reserve seats',
                    confirmButtonColor: '#DC5154',
                    confirmButtonText: 'OK',
                    type: 'error',
                }).then((confirm)=>{
                    this.set('seats',[]);
                    this.get('router').transitionTo('login');
                })
            }
            else{

            let reservation = Ember.Object.create({
                cinemaShowingId: cId,
                userId: uId,
                seats: this.get('seats')
              });
            sweetAlert({
                title: 'You selected the following seats: ' + this.get('seats'),
                confirmButtonText: 'Reserve seats',
                showCancelButton: true,
                cancelButtonText: 'Abort reservation',
                confirmButtonColor: '#DC5154',
                type: 'info'
            }).then((confirm)=>{
                this.get('_cinemaService').createReservation(reservation,token).then(response=>{
                    let reservationId = String(response);
                    console.log("TEMP");

                    sweetAlert({
                        title: 'Successfuly reserved seats',
                        confirmButtonText: 'OK',
                        confirmButtonColor: '#DC5154',
                        type: 'success',
                    }).then((confirm)=> {
                        this.set('seats',[]);
                        console.log(reservationId);
                        this.get('router').transitionTo('payment',[reservationId]); // response is reservationId        
                    })     
                }).catch((error) =>{
                    let message =JSON.parse(error.responseText);
                    sweetAlert({
                    title: message.error.description,
                    confirmButtonText: 'OK',
                    confirmButtonColor: '#DC5154',
                    type: 'error'
                });  
            })         
            })
        }
        }      
    }
});
