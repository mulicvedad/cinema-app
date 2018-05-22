import Ember from 'ember';
import Base from 'ember-simple-auth/authenticators/base';

const {RSVP: { Promise }} = Ember;

export default Base.extend({
    cinemaAppHttp: Ember.inject.service('cinema-app-http'),

    restore(data) {
        console.log('restore');
        console.log(data);
        return new Promise((resolve, reject) => {
            if (!Ember.isEmpty(data.jwt)) {
                resolve(data);
            } else {
                reject();
            }
        });
    },

    authenticate(credentials, callback) {
        return this.get('cinemaAppHttp').post('auth/login', credentials, (resp) => {
            if (callback) {
                console.log(resp);
                callback(resp);
            }

            return resp;
        });
    },

    invalidate(data) {
        return Promise.resolve(data);
    }
});
