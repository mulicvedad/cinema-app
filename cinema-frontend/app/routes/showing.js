import Ember from 'ember';
const { inject: {service}} = Ember;


export default Ember.Route.extend({
  session: Ember.inject.service('session'),
  queryParams: {
    date: {
      refreshModel: true
    }, 
    page: {
      refreshModel: true
    },
    perPage: {
      refreshModel: true
    }
  },
   currDate:'',
   nextPage:'',
   numberPerPage:'',
  
  _cinemaService: service('cinema-service'),
  model: function (params) {

    let token = "";
    if(this.get('session.isAuthenticated'))
      token = this.get('session.data.authenticated.jwt');
    if(params.date) {
      console.log(params.date);
      this.set('currDate', params.date);
      this.set('nextPage', params.page);
      this.set('numberPerPage', params.perPage);
      return this.get('_cinemaService').getShowingByDate(params.date, params.page, params.perPage);
    } else {
    return  this.get('_cinemaService').getUpcomingShowing(params.page, params.perPage);
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
    },

   getNextPage: function(totalPages) {
      if(this.get('nextPage') + 1 < totalPages) {
          this.get('_cinemaService').getUpcomingShowing(this.get('nextPage') + 1, this.get('numberPerPage'));
        this.controller.set('pageToDisplay', this.get('nextPage')+2);
        this.transitionTo({queryParams: { page: this.get('nextPage') + 1 }});
      }
    },
    getPreviousPage: function() {
      if(this.get('nextPage') > 0){
        this.get('_cinemaService').getUpcomingShowing(this.get('nextPage') - 1, this.get('numberPerPage'));
        this.controller.set('pageToDisplay', this.get('nextPage'));
        this.transitionTo({queryParams: { page: this.get('nextPage') - 1}});
      }
    } 
  }
});
