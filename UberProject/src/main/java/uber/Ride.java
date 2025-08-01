package uber;

public class Ride {

    public GeoLocation startingLocation;
    public GeoLocation destinationLocation;
    public Driver currentDriver;
    public Rider currentRider;


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

}
