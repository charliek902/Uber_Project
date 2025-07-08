package uber;

public class RideRequest {
    Integer size;
    GeoLocation start;
    GeoLocation destination;
    String requirements;
    Trip rideType;
    Rider rider;
    Driver driver;
    RequestType requestType;
    public RideRequest(){}
}
