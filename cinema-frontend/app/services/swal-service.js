import Ember from 'ember';

export default Ember.Service.extend({
  error(message) {
    sweetAlert({
      title: message,
      confirmButtonText: 'Try again',
      confirmButtonColor: '#DC5154',
      type: 'error'
    });
  },
  success(title, callback) {
    sweetAlert({
      title: title,
      confirmButtonText: 'OK',
      confirmButtonColor: '#DC5154',
      type: 'success'
    })
    .then(callback);
  }
});


