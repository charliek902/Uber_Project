package uber;

public class TripService {

    ServerSocketLayer serverSocketHandler;
    MatchingEngine matchingEngine;

    public TripService(ServerSocketLayer serverSocketHandler, MatchingEngine matchingEngine) {
        this.serverSocketHandler = serverSocketHandler;
        this.matchingEngine = matchingEngine;
    }

    public void acceptRider () {

    }

    public void acceptDriver() {

    }

    public void cancelTrip (Request request) {
        // when trip cancelled, we add Driver back to engine
        // we update the other rider / driver that it has been completed
    }

    public void completeTrip(Request request) {
        // when trip completed, we add Driver back to engine
        // we update the other rider / driver that it has been completed
    }

}
