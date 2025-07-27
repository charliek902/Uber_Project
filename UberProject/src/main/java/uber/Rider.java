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

    public void setRequirements(String requirements){
        this.requirements = requirements;
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


}
