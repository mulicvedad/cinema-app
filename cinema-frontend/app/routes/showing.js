import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  queryParams: {
    date: {
      refreshModel: true
    }
  },

  _cinemaService: service('cinema-service'),

  model: function (params) {
    if(params.date) {
      return this.get('_cinemaService').getShowingByDate(params.date);
    } else {
    return  this.get('_cinemaService').getUpcomingShowing();
    }
  },

});
