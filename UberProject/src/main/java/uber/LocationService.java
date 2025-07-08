package uber;
import java.util.ArrayList;

public class LocationService {
    // implement the GeoHash here so that locations can be updated

    public static ArrayList<Driver> getNearbyDrivers(RideRequest request){
        if(request.rider == null){
            return null;
        }
        return new ArrayList<Driver>();
    }

    public static GeoLocation requestLocation(RideRequest request){

        return new GeoLocation();
    }



}
