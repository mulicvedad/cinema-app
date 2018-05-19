import Ember from 'ember';
import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

  currentUser: null,

  createUser() {
    let newUser = Ember.Object.create({
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      username:'',
    });
    this.set('currentUser', newUser);
    return this.get('currentUser');
  },

  getUserDetails: function(id) {
    return this.ajax('GET', `/user/users/${id}/details`);
  },

  registerUser: function (user) {
    return this.ajax('POST', '/user/users/register', user);
  },

  deleteUser: function(id) {
  	return this.ajax('DELETE', `/user/users/${id}`);
  },

  updateUser: function(id, user) {
  	return this.ajax('PUT', `/user/users/${id}`, user);
  },

  resetPassword: function(id, user) {
  	return this.ajax('PUT', `/user/users/${id}/reset-password`, user)
  }
});

