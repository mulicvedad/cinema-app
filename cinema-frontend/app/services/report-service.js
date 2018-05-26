import Ember from 'ember';

export default Ember.Service.extend({
  generateReport() {
    Ember.$.ajax({
      url: '/cinema/cinema-showings/report',
      method: 'GET',
      responseType: 'arraybuffer'
    }).then( response => {
      /* old version - didn't work
      let file = new Blob([response], {type: 'application/pdf'});
      let fileUrl = window.URL.createObjectURL(file);
      let linkElement = document.createElement('a');
      linkElement.setAttribute('href', fileUrl);
      linkElement.setAttribute('download', 'upcoming_showings.pdf');
      */
      let linkElement = document.createElement('a');
      linkElement.setAttribute('href', 'http://localhost:9093/cinema/cinema-showings/report');
      linkElement.click();
    }).catch(error => {
      console.log('Error occured during pdf download: ' + error);
    });
  },
});
