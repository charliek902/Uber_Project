package uber;

public class Rider extends User implements Client{
    Driver currentDriver;
    String requirements;

    public Rider(String fullName, GeoLocation location, String email) {
        super(fullName, location, email);
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
    public void createConnection() {

    }

    @Override
    public void closeConnection() {

    }

    @Override
    public GeoLocation requestLocation() {
        if(this.getCurrentDriver() == null){
            return null;
        }
        RideRequest req = new RideRequestBuilder(new RideRequest())
                .setDriver(this.currentDriver)
                .setRider(this)
                .setRequestType(RequestType.DRIVER_LOCATION)
                .build();
        return LocationService.requestLocation(req);
    }

    public void updateLocation(){

    }

    public void cancelTrip(){

    }
    public void completeTrip(){

    }

}
