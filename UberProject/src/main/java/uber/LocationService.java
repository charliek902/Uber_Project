package uber;

public class LocationService {
    ServerSocketHandler serverSocketHandler;
    MatchingEngine matchingEngine;

    public LocationService(ServerSocketHandler serverSocketHandler, MatchingEngine matchingEngine) {
        this.serverSocketHandler = serverSocketHandler;
        this.matchingEngine = matchingEngine;
    }

    public void updateLocation(Request request){
        // also need to update the engine if driver / rider does not have a client yet

    }

    public void getLocation(Request request){

    }

}
