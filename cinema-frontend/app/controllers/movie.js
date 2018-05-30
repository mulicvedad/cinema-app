import Ember from 'ember';
import SweetAlertMixin from 'ember-sweetalert/mixins/sweetalert-mixin';
 
const {
  inject: {
    service
  }
} = Ember;

export default Ember.Controller.extend(SweetAlertMixin, {
  session: Ember.inject.service(),
  _movieService: service('movie-service'),

	 actions: {
    selectShowing: function (value) {
      //ovdje cemo transitionat na rezervacije, sa id-em showinga (tako da mozemo iz showing objekta izvuci i id filma, ne moramo njega
      //proslijedjivati)
      this.transitionToRoute('reservation', value);
    },

    addReview(uId, uname, mId,token){
      let sweetAlert = this.get('sweetAlert');
      sweetAlert({
        input: 'textarea',
        text: 'Submitted by: ' + uname,
        confirmButtonText: 'Submit',
        showCancelButton: true,
        confirmButtonColor: '#DC5154',
        cancelButtonText: 'Abort',
        type: 'info'
      }).then((text)=>{
        let review = Ember.Object.create({
          userId: uId,
          username: uname,
          comment: text,
          movieId: mId,
        });
        this.get('_movieService').createReview(review,token).then(()=>{
          sweetAlert({
            title: 'Successfuly submitted review',
            confirmButtonText: 'OK',
            confirmButtonColor: '#DC5154',
            type: 'success'}).then((confirm)=>{
              this.get('target.router').refresh();
            })
          });  
        })
      }
    }
  });