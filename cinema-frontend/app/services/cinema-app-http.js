import Ember from 'ember';

const { $: { ajax }, run } = Ember;
const { RSVP } = Ember;

export default Ember.Service.extend({
    _innerCreateMethod(route, data, httpRequest, happyPath, unhappyPath) {
        let requestOptions = {
            url: `/${route}`,
            contentType: 'application/json',
            type: httpRequest,
            dataType: 'json'
        };

        if (data) {
            requestOptions.data = JSON.stringify(data);
        }

        return new RSVP.Promise((resolve, reject) => {
            ajax(requestOptions).then((response) => {
                run(() => {
                    if (happyPath) {
                        return resolve(happyPath(response));
                    }
                    else {
                        return resolve();
                    }
                });
                }, (error) => {
                    run(() => {
                        if (unhappyPath) {
                            return unhappyPath(error);
                        }

                        return reject(error);
                    });
            });
        });
    },

    get(route, data, happyPath, unhappyPath) {
        return this._innerCreateMethod(route, null, 'GET', happyPath, unhappyPath);
    },

    post(route, data, happyPath, unhappyPath) {
        return this._innerCreateMethod(route, data, 'POST', happyPath, unhappyPath);
    }
});