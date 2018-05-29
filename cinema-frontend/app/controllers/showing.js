import Ember from 'ember';
import moment from 'moment';

export default Ember.Controller.extend({
    queryParams: ['date', 'page', 'perPage'],
    page: 0,
    perPage: 3,
    pageToDisplay: 1,

    session: Ember.inject.service('session'),
    today: moment().format('YYYY-MM-DD'),
    showingDate:null,
    nextMonday: moment().day("Monday").format('YYYY-MM-DD'),
    nextTuesday: moment().day("Tuesday").format('YYYY-MM-DD'),
    nextWednesday: moment().day("Wednesday").format('YYYY-MM-DD'),
    nextThursday: moment().day("Thursday").format('YYYY-MM-DD'),
    nextFriday: moment().day("Friday").format('YYYY-MM-DD'),
    nextSaturday: moment().day("Saturday").format('YYYY-MM-DD'),
    nextSunday: moment().day("Sunday").format('YYYY-MM-DD'),

});
