import Ember from 'ember';

const {
  inject: {
    service
  }
} = Ember;

export default Ember.Route.extend({

  _movieService: service('movie-service'),

   model: function (params) {
    // params.id is the movie id we passed from the showing list, we'll use it to fetch the movie details from the movie service
      console.log(params.id);
      //return this.get('_movieService').getMovieById(params.id);
      return  {
    "id": 1,
    "movieTitle": "Mad Max",
    "overview": "In 2002, an artistically inclined seventeen-year-old girl comes of age in Sacramento, California.",
    "tmdbId": null,
    "genres": [
      {
        "id": 1,
        "name": "Drama"
      }
    ],
    "reviews": [
      {
        "id": 1,
        "userId": 1,
        "comment": "Great movie!"
      }
    ],
    "moviePeople": [
      {
        "id": 1,
        "name": "Greta Gerwig",
        "roles": [
          {
            "id": 1,
            "role": "Director"
          }
        ]
      }
    ],
    "release_date": "2017-11-03T09:10:10.000+0000",
    "posterPath": "http://image.tmdb.org/t/p/w780/kqjL17yufvn9OVLyXYpvtyrFfak.jpg"
  }
    }

});
