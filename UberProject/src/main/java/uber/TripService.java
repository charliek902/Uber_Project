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
        request.currentUser.setCurrentRide(null);
        this.matchingEngine.addDriver((Driver) request.currentUser);
        this.serverSocketHandler.sendMessageFromClient(request);
    }

}
