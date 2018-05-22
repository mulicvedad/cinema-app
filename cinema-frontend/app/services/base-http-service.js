import Ember from 'ember';

export default Ember.Service.extend({
  ajax(method, url, data, token) {
    return Ember.$.ajax({
      url: url,
      method: method,
      data: data ? JSON.stringify(data) : null,
      contentType: 'application/json',
      beforeSend: function (xhr) {
        xhr.setRequestHeader("Authorization", token);
     },
     
    });
  },
});