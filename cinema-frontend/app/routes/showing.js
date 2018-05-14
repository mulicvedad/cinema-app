import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({

  _cinemaService: service('cinema-service'),


  model: function () {
    return this.get('_cinemaService').getUpcomingShowing();
  },

});
