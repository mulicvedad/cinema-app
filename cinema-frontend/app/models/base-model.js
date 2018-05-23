import Ember from 'ember';

export default Ember.Object.extend({
  modelProperties: [],

  getModelProperties: function() {
    return this.get('modelProperties');
  },

  serialize: function() {
    var obj = {};
    var _modelProperties = this.getModelProperties();
    for(var property of _modelProperties) {
      obj[property] = this.get(property);
    }
    return JSON.stringify(obj);
  },
  deserialize: function() {}

});
