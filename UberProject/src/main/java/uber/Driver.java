package uber;
import java.util.ArrayList;

public class Driver extends User {
    public Integer size;
    public Trip tripType;
    public Rider rider;
    public Boolean isLocked;

    // USE THE STATE PATTERN FOR MOVEMENT

    public Driver(Integer id, String fullName, GeoLocation location, String email, LocationService locationService, MessageService messageService, TripService tripService, Integer size, Trip driverTripType, ConnectionDB db) throws Exception {
        super(id, fullName, location, email, locationService, messageService, tripService, db);
        this.size = size;
        this.tripType = driverTripType;
        this.isLocked = false;
    }

    public void setCurrentRider(Rider rider){
        this.rider = rider;
    }

    public Rider getCurrentRider(){
        return this.rider;
    }


    @Override
    public void cancelTrip(){
        Request request = new RequestBuilder(new Request())
                .setCurrentUser(this)
                .setRider(this.rider)
                .setDriver(this)
                .setCurrentRequestTime(0)
                .setTimeOut(300)
                .setSize(this.size)
                .setRequestType(RequestType.CANCEL_TRIP)
                .validate()
                .build();
        this.tripService.cancelTrip(request);
    }

    @Override
    public void completeTrip() {
        Request request = new RequestBuilder(new Request())
                .setCurrentUser(this)
                .setRider(this.rider)
                .setDriver(this)
                .setCurrentRequestTime(0)
                .setTimeOut(300)
                .setSize(this.size)
                .setRequestType(RequestType.COMPLETE_TRIP)
                .validate()
                .build();
        this.tripService.completeTrip(request);
    }

    @Override
    public void updateLocation() {
        Request request = new RequestBuilder(new Request())
                .setStartingLocation(this.currentLocation)
                .setCurrentUser(this)
                .setRequestType(RequestType.UPDATE_LOCATION)
                .validate()
                .build();
        Response response = this.locationService.updateLocation(request);
    }

    public void move() {
        GeoLocation currentLocation = this.getCurrentLocation();
//        this.setCurrentLocation(this.randomizeLocation(currentLocation.getLongitude(), currentLocation.getLatitude()));
    }

//    private GeoLocation randomizeLocation(Double longitude, Double latitude) {
//        int movementSignLongitude = (int)(Math.random() * 2);
//        int movementSignLatitude = (int)(Math.random() * 2);
//        int steps = 100;
//        Integer newLongitude = movementSignLongitude == 1 ?  longitude + (Math.random() * steps) :  longitude + (Math.random() * (steps * -1));
//        Integer newLatitude = movementSignLatitude == 1 ?  latitude + (Math.random() * steps) :  latitude + (Math.random() * (steps * -1));
//        return new GeoLocation(newLongitude, newLatitude);
//    }

}
