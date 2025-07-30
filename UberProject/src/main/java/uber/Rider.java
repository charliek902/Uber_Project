package uber;

public class Rider extends User{
    Driver currentDriver;
    Integer size;

    public Rider(Integer id, String fullName, GeoLocation location, String email, LocationService locationService, MessageService messageService, TripService tripService, ConnectionDB db, Integer size) throws Exception {
        super(id, fullName, location, email, locationService, messageService, tripService, db);
        this.size = size;
    }

    public Driver getCurrentDriver(){
        return currentDriver;
    }

    public void setCurrentDriver(Driver driver){
        this.currentDriver = driver;
    }

    public void move() {
        if (this.currentRide == null) {
            this.setCurrentLocation(this.randomizeLocation(this.getCurrentLocation().getLongitude(), this.getCurrentLocation().getLatitude()));
        }
    }

    private GeoLocation randomizeLocation(Integer longitude, Integer latitude) {
        int movementSignLongitude = (int)(Math.random() * 2);
        int movementSignLatitude = (int)(Math.random() * 2);
        int steps = 100;
        Double newLongitude = movementSignLongitude == 1 ? longitude + Math.ceil((Math.random() * steps)) :  longitude + (Math.random() * (steps * -1));
        Double newLatitude = movementSignLatitude == 1 ?  latitude + (Math.random() * steps) :  latitude + (Math.random() * (steps * -1));
        return new GeoLocation(newLongitude.intValue(), newLatitude.intValue());
    }

    @Override
    public void cancelTrip(){
        Request request = new RequestBuilder(new Request())
                .setCurrentUser(this)
                .setRider(this)
                .setDriver(this.currentDriver)
                .setCurrentRequestTime(0)
                .setTimeOut(300)
                .setSize(this.size)
                .setRequestType(RequestType.CANCEL_TRIP)
                .validate()
                .build();
        this.tripService.endTrip(request);
    }

    @Override
    public void completeTrip(){
        Request request = new RequestBuilder(new Request())
                .setCurrentUser(this)
                .setRider(this)
                .setDriver(this.currentDriver)
                .setCurrentRequestTime(0)
                .setTimeOut(300)
                .setSize(this.size)
                .setRequestType(RequestType.COMPLETE_TRIP)
                .validate()
                .build();
        this.tripService.endTrip(request);
    }

    @Override
    public void updateLocation() {
        Request request = new RequestBuilder(new Request())
                .setStartingLocation(this.currentLocation)
                .setCurrentUser(this)
                .setRide(this.getCurrentRide())
                .setRequestType(RequestType.UPDATE_LOCATION)
                .validate()
                .build();
        Response response = this.locationService.updateLocation(request);
    }


    public void getDriver() {
        Request request = new RequestBuilder(new Request())
                .setRequestType(RequestType.FIND_DRIVERS)
                .setStartingLocation(this.currentLocation)
                .setCurrentUser(this)
                .validate()
                .build();
        Response response = this.locationService.getDriver(request);
        this.setCurrentDriver(response.driver);
    }
}
