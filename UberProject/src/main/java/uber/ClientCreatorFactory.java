package uber;

import java.util.ArrayList;

public class ClientCreatorFactory {

    LocationService locationService;
    MessageService messageService;
    TripService tripService;

    ClientCreatorFactory(LocationService locationService, MessageService messageService, TripService tripService) {
        this.locationService = locationService;
        this.messageService = messageService;
        this.tripService = tripService;
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
