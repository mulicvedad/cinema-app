import Ember from 'ember';
import LoginRoute from './login';
const { inject: {service}} = Ember;


export default Ember.Route.extend({
  session: Ember.inject.service('session'),
  queryParams: {
    date: {
      refreshModel: true
    }
  },
  _cinemaService: service('cinema-service'),
  model: function (params) {
    let token = null;
    if(this.get('session.isAuthenticated'))
      token = this.get('session.data.authenticated.jwt');
    if(params.date) {
      return this.get('_cinemaService').getShowingByDate(params.date,token);
    } else {
    return  this.get('_cinemaService').getUpcomingShowing(token);
    }
  },

});
