import Ember from 'ember';
import moment from 'moment';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Controller.extend({
  _movieService: service('movie-service'),
  _swalService: service('swal-service'),
  today: moment().format('YYYY-MM-DD'),
  session: Ember.inject.service(),
  selectedGenres: [],
  actions: {
    setSelectedGenre(genreId) {
      let allGenres = this.get('model.genres');
      let currentGenre = allGenres.find( g => g.id == genreId);
      this.get('model.newMovie.genres').push(currentGenre);
      document.getElementById('dropdownGenre').options[genreId].disabled = true;
    },
    clearSelectedGenres() {
      let options = document.getElementById('dropdownGenre').options;
      for(let i = 0; i < options.length; i++) {
        options[i].disabled = false;
      }
    },
    addNewMovie(token) {
      this.set('model.newMovie.movie.largePosterPath', this.get('model.newMovie.movie.posterPath'));
      this.get('_movieService').createNewMovie(this.get('model.newMovie'), token).then( response => {
        this.get('_swalService').succes("New movie added successfully");
      }).catch( error => {
        console.log('Error: ' + error);
      })
    }
  }

});
