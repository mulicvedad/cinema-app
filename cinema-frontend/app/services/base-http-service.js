import Ember from 'ember';

export default Ember.Service.extend({
  ajax(method, url, data) {
    return Ember.$.ajax({
      url: url,
      method: method,
      data: data ? JSON.stringify(data) : null,
      contentType: 'application/json'
    });
  },
});