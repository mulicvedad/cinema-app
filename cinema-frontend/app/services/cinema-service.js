import Ember from 'ember';

import BaseHttpService from './base-http-service';

export default BaseHttpService.extend({

	getShowingByDate: function(date,token) {
		return this.ajax('GET', '/cinema/cinema-showings?date=' + date,null,token);
	},

	getUpcomingShowing: function(token) {
		return this.ajax('GET', '/cinema/cinema-showings/upcoming',null,token);
	},

});

