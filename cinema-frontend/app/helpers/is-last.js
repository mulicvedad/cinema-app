import Ember from 'ember';

export default Ember.Helper.helper(function(params) {
    let [obj, collection] = params;
    let isLast = false;
    let index = collection.indexOf(obj);
    if (index == collection.length - 1)
      isLast = true;
    return isLast;
});
