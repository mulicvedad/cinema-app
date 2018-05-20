import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('showing');
  this.route('login');
  this.route('signup');
  this.route('movie', { path: '/:id/details'});
  this.route('reservation', { path: '/:id/reservation'});
  this.route('payment');
});

export default Router;
