import Ember from 'ember';
import moment from 'moment';

export default Ember.Controller.extend({
    session: Ember.inject.service('session'),
    today: moment().format('YYYY-MM-DD'),
    showingDate:null
});
