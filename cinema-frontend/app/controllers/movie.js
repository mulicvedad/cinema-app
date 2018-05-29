import Ember from 'ember';

export default Ember.Controller.extend({
	 actions: {
    selectShowing: function (value) {
      //ovdje cemo transitionat na rezervacije, sa id-em showinga (tako da mozemo iz showing objekta izvuci i id filma, ne moramo njega
      //proslijedjivati)
      this.transitionToRoute('reservation', value);
    },
  }
});
