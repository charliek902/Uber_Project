package uber;

import java.util.*;

public class Driver extends User implements Client {
    public Integer size;
    public Trip tripType;
    public Rider rider;

    public Driver(String fullName, GeoLocation location, String email, Integer size, Trip driverTripType) {
        super(fullName, location, email);
        this.size = size;
        this.tripType = driverTripType;
    }

    public void setCurrentRider(Rider rider){
        this.rider = rider;
    }

    public Rider getCurrentRider(){
        return this.rider;
    }

    public ArrayList<Rider> getNearbyRiders(){
        return new ArrayList<Rider>();
    }

    public Notification sendRiderNotification(){
        return new Notification();
    }

    @Override
    public void createConnection() {

    }

    @Override
    public void closeConnection() {

    }

    @Override
    public GeoLocation requestLocation() {
        if(this.getCurrentRider() == null){
            return null;
        }
        // TODO: implement request to location
        return new GeoLocation();
    }

    public void updateLocation(){

    }

    public void cancelTrip(){

    }
    public void completeTrip(){

    }

}
