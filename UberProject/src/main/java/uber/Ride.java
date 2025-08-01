package uber;

public class Ride {

    public GeoLocation startingLocation;
    public GeoLocation destinationLocation;
    public Driver currentDriver;
    public Rider currentRider;


    public Ride(Rider rider, Driver driver, GeoLocation destination, GeoLocation startingLocation){
        this.startingLocation = startingLocation;
        this.destinationLocation = destination;
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
