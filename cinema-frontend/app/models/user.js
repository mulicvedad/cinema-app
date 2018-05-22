import BaseModel from './base-model';

var _modelProperties = ['username', 'password'];

export default BaseModel.extend({
	modelProperties: _modelProperties,
});
