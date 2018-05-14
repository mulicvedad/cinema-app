import Ember from 'ember';

export default Ember.Route.extend({

  model: function () {
    return Ember.A([
  {
    "id": 1,
    "title": "Mad Max",
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
    "poster_path": "http://image.tmdb.org/t/p/w185/kqjL17yufvn9OVLyXYpvtyrFfak.jpg"
  },
  {
    "id": 1,
    "title": "Blade Runner",
    "overview": "In 2002, an artistically inclined seventeen-year-old girl comes of age in Sacramento, California.",
    "tmdbId": null,
    "genres": [
      {
        "id": 1,
        "name": "Action"
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
    "poster_path": "https://images-na.ssl-images-amazon.com/images/M/MV5BNzA1Njg4NzYxOV5BMl5BanBnXkFtZTgwODk5NjU3MzI@._V1_SX300.jpg"
  },
    {
    "id": 1,
    "title": "Call me by your name",
    "overview": "In 2002, an artistically inclined seventeen-year-old girl comes of age in Sacramento, California.",
    "tmdbId": null,
    "genres": [
      {
        "id": 1,
        "name": "Romance"
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
    "poster_path": "https://images-na.ssl-images-amazon.com/images/M/MV5BNDk3NTEwNjc0MV5BMl5BanBnXkFtZTgwNzYxNTMwMzI@._V1_SX300.jpg"
  }, {
    "id": 1,
    "title": "Interstellar",
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
    "poster_path": "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"
  },
  {
    "id": 1,
    "title": "Coco",
    "overview": "In 2002, an artistically inclined seventeen-year-old girl comes of age in Sacramento, California.",
    "tmdbId": null,
    "genres": [
      {
        "id": 1,
        "name": "Cartoon"
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
    "poster_path": "https://images-na.ssl-images-amazon.com/images/M/MV5BYjQ5NjM0Y2YtNjZkNC00ZDhkLWJjMWItN2QyNzFkMDE3ZjAxXkEyXkFqcGdeQXVyODIxMzk5NjA@._V1_SX300.jpg"
  },
    {
    "id": 1,
    "title": "Lady Bird",
    "overview": "In 2002, an artistically inclined seventeen-year-old girl comes of age in Sacramento, California.",
    "tmdbId": null,
    "genres": [
      {
        "id": 1,
        "name": "Romance"
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
    "poster_path": "https://images-na.ssl-images-amazon.com/images/M/MV5BODhkZGE0NDQtZDc0Zi00YmQ4LWJiNmUtYTY1OGM1ODRmNGVkXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg"
  }
])
  },

});
