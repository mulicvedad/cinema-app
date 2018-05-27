import Ember from 'ember';
const { inject: {service}} = Ember;


export default Ember.Route.extend({
  session: Ember.inject.service('session'),
  queryParams: {
    date: {
      refreshModel: true
    }
  },
   currDate:'',

  _cinemaService: service('cinema-service'),
  model: function (params) {
    let token = "";
    if(this.get('session.isAuthenticated'))
      token = this.get('session.data.authenticated.jwt');
    if(params.date) {
      console.log(params.date);
      this.set('currDate', params.date);
      return this.get('_cinemaService').getShowingByDate(params.date);
    } else {
    return  this.get('_cinemaService').getUpcomingShowing();
    }
  },

  setupController(controller, model,params) {
    // Call _super for default behavior
    this._super(controller, model);
    // Implement your custom setup after
    controller.set('showingDate',params.queryParams.date);
  },

  actions: {
    seeDetails: function(id) {
      this.transitionTo('movie', id,  { queryParams: { date: this.get('currDate') }});
    }
  }

});
