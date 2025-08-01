package uber;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Request {
    public Integer size;
    public GeoLocation start;
    public GeoLocation destination;
    public GeoLocation newLocation;
    public GeoLocation pastLocation;
    @JsonIgnore
    public Rider rider;
    @JsonIgnore
    public Driver driver;
    public RequestType requestType;
    public Notification notification;
    public Integer time;
    public Integer timeout;
    public User currentUser;
    @JsonIgnore
    public ClientSocket connectedUserSocket;
    public Ride ride;
    public ArrayList<Driver> matchingDrivers;
    public Request(){}
}
