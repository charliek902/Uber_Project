package uber;

public class TripService {

    ServerSocketHandler serverSocketHandler;
    MatchingEngine matchingEngine;

    public TripService(ServerSocketHandler serverSocketHandler, MatchingEngine matchingEngine) {
        this.serverSocketHandler = serverSocketHandler;
        this.matchingEngine = matchingEngine;
    }

    public void getNearbyDrivers() {
        // hits the matching engine, returns the possible drivers, requests them all until there is a match
    }

    public void acceptRider () {

    }

    public void cancelTrip () {

    }

    public void completeTrip() {
        // when trip completed, we add Driver back to engine
        // we update the other rider / driver that it has been completed
    }

}
