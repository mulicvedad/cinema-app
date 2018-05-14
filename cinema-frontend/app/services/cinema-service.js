import Ember from 'ember';

import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

	getShowingByDate: function(date) {
		return this.ajax('GET', '/cinema/cinema-showings?date=', date)
	},

	getUpcomingShowing: function() {
		return this.ajax('GET', '/cinema/cinema-showings/upcoming')
	},

});

