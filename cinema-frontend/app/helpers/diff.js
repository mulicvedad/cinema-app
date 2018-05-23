import Ember from 'ember';

const diff = (params) => params[0] != params[1];
export default Ember.Helper.helper(diff);
