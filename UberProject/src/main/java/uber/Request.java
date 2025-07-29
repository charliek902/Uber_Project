package uber;
import java.util.ArrayList;

public class Request {
    Integer size;
    GeoLocation start;
    GeoLocation destination;
    Trip rideType;
    Rider rider;
    Driver driver;
    RequestType requestType;
    Integer time;
    Integer timeout;
    User currentUser;
    User connectedUser;
    ClientSocket connectedUserSocket;
    Ride ride;
    ArrayList<Driver> matchingDrivers;
    public Request(){}
}
