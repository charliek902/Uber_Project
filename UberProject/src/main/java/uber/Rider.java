package uber;

public class Rider extends User{
    Driver currentDriver;
    String requirements;
    Integer size;

    public Rider(Integer id, String fullName, GeoLocation location, String email, LocationService locationService, MessageService messageService, TripService tripService, Integer size) {
        super(id, fullName, location, email, locationService, messageService, tripService);
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


}
