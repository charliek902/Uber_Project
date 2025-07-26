package uber;

public class Request {
    Integer size;
    GeoLocation start;
    GeoLocation destination;
    String requirements;
    Trip rideType;
    Rider rider;
    Driver driver;
    RequestType requestType;
    Integer time;
    Integer timeout;
    public Request(){}
}
