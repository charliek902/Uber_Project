package uber;

import java.util.ArrayList;

public class TripService {

    ServerSocketLayer serverSocketHandler;
    MatchingEngine matchingEngine;

    public TripService(ServerSocketLayer serverSocketHandler, MatchingEngine matchingEngine) {
        this.serverSocketHandler = serverSocketHandler;
        this.matchingEngine = matchingEngine;
    }

    public void acceptRider (Request request) {
        this.matchingEngine.removeDriver((Driver) request.currentUser);
        this.serverSocketHandler.sendMessageFromClient(request);
    }

    public void endTrip(Request request) {
        this.endTrips(request);
        this.matchingEngine.addDriver((Driver) request.currentUser);
        this.serverSocketHandler.sendMessageFromClient(request);
    }

    private void endTrips(Request request) {
        if (request.currentUser instanceof Driver) {
            request.driver.setCurrentRide(null);
        } else {
            request.rider.setCurrentRide(null);
        }
    }
}
