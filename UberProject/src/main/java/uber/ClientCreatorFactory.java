package uber;
import java.util.ArrayList;

public class ClientCreatorFactory {

    LocationService locationService;
    MessageService messageService;
    TripService tripService;
    ConnectionDB db;

    ClientCreatorFactory(LocationService locationService, MessageService messageService, TripService tripService, ConnectionDB db) {
        this.locationService = locationService;
        this.messageService = messageService;
        this.tripService = tripService;
        this.db = db;
    }

    public ArrayList<Driver> createDrivers(Integer numDrivers) {

        return new ArrayList<Driver>();
    }

    public ArrayList<Rider> createRiders(Integer numRiders) {
        return new ArrayList<Rider>();
    }

    private void randomize () {

    }

}
