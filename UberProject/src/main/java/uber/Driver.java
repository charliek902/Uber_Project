package uber;
import java.util.*;

public class Driver extends User {
    public Integer size;
    public Trip tripType;
    public Rider rider;
    public Boolean isLocked;

    public Driver(Integer id, String fullName, GeoLocation location, String email, LocationService locationService, MessageService messageService, TripService tripService, Integer size, Trip driverTripType) {
        super(id, fullName, location, email, locationService, messageService, tripService);
        this.size = size;
        this.tripType = driverTripType;
        this.isLocked = false;
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

    public Message sendRiderNotification(){
        return new Message();
    }


    public void updateLocation(){

    }

    public void cancelTrip(){

    }

    public void completeTrip(){

    }

}
