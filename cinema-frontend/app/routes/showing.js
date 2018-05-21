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
   currDate:'',

  _cinemaService: service('cinema-service'),

  model: function (params) {
    if(params.date) {
      console.log(params.date);
      this.set('currDate', params.date);
      return this.get('_cinemaService').getShowingByDate(params.date);
    } else {
    return  this.get('_cinemaService').getUpcomingShowing();
    }
  },

  actions: {
    seeDetails: function(id) {
      this.transitionTo('movie', id,  { queryParams: { date: this.get('currDate') }});
    }
  }

});
