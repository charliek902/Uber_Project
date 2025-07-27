package uber;

public class Rider extends User{
    Driver currentDriver;
    String requirements;
    Integer size;

    public Rider(Integer id, String fullName, GeoLocation location, String email, LocationService locationService, MessageService messageService, TripService tripService, ConnectionDB db, Integer size) {
        super(id, fullName, location, email, locationService, messageService, tripService, db);
        this.size = size;
    }

    public Driver getCurrentDriver(){
        return currentDriver;
    }

    public void setCurrentDriver(Driver driver){
        this.currentDriver = driver;
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
        this.tripService.cancelTrip(request);
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
        this.tripService.completeTrip(request);
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
                .setRideType(Trip.NORMAL)
                .setCurrentUser(this)
                .validate()
                .build();
        Response response = this.locationService.getDriver(request);
        this.setCurrentDriver(response.driver);
    }
}
