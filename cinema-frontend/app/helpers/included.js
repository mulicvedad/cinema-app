import Ember from 'ember';

export default Ember.Helper.helper(function(params) {
    console.log('AT IT');
    let [seatId, availableSeats] = params;
      let isIncluded = false;
    for(let i = 0; i < availableSeats.length; i++) {
        if(seatId == availableSeats[i].id)
            isIncluded = true;
    }
    console.log(isIncluded);
    return isIncluded;
});
