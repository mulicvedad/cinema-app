import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({
  _cinemaService: service('cinema-service'),

  model(params) {
    return this.get('_cinemaService').getAllNews();
  },
});
