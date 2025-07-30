package uber;

public class Ride {

    private GeoLocation startingLocation;
    private GeoLocation destinationLocation;
    private Driver currentDriver;
    private Rider currentRider;


    public Ride(Rider rider, Driver driver, Request req){
        this.startingLocation = req.start;
        this.destinationLocation = req.destination;
        this.currentDriver = driver;
        this.currentRider = rider;
    }

    public GeoLocation getStartingLocation() {
        return this.startingLocation;
    }

    public GeoLocation getDestinationLocation() {
        return this.destinationLocation;
    }

    public String toJSON() {
        return "";
    }
}
