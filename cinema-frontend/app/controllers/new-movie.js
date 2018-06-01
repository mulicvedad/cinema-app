import Ember from 'ember';

const { inject: {service}} = Ember;

export default Ember.Controller.extend({
    _movieService: service('movie-service'),
    _swalService: service('swal-service'),
    session: Ember.inject.service(),
    overviewLength: 2,
    poster: null,
    actions: {
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
