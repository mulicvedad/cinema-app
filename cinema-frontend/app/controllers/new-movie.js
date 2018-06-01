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
  overviewLength: 2,
  poster: null,
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
    },
    setSelectedMovie(movieId) {
      this.set('model.movie.tmdbId', movieId);
      let movie =  this.get('model.moviesTMDB').find( m => m.id == movieId);
      console.log("MOVIE:");
      console.log(movie);
      this.set('model.movie.title', movie.title);
      this.set('model.movie.overview', movie.overview);
      this.set('model.movie.largePosterPath',movie.largePosterPath);
      this.set('model.movie.tmdbId',movie.movieId);
      this.set('model.movie.genres',[]);
      this.set('model.movie.reviews',[]);
      this.set('model.movie.moviePeople',[]);
      this.set('model.movie.release_date',movie.release_date);
      console.log("RELEASE DATE:");
      console.log(movie.release_date);
      this.set('model.movie.poster_path',movie.poster_path);
      this.set('model.movie.vote_average', movie.vote_average);
      this.set('model.movie.original_title', movie.original_title);
      this.set('overviewLength',movie.overview.length / 50);
      this.set('poster', "http://image.tmdb.org/t/p/w342" + movie.poster_path);
      console.log("ORIGINAL TITLE:");
      console.log(movie.original_title);
    },
    addMovieFromTMDB(token) {
        let selectedMovie = this.get('model.movie');
        if(selectedMovie.title == null || selectedMovie.overview == null
      || selectedMovie.poster_path == null) {
          this.get('_swalService').error("Required fields are null.");
          return;
      }
      let MovieRequest = Ember.Object.create({
          movie: selectedMovie,
          genres: [],
          moviePeople: []
      });
      this.get('_movieService').createNewMovie(MovieRequest,token).then( response => {
          this.get('_swalService').success("Movie from the MovieDB successfully added",
            confirm => { this.transitionToRoute('showing'); });
        }).catch( errorResponse => {
          this.get('_swalService').error("An error occured while creating new movie from the MovieDb.\n Error details:\n" + errorResponse);
        });
    }
  }

});
