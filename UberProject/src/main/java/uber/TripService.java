package uber;

public class TripService {

    ServerSocketHandler serverSocketHandler;
    public TripService(ServerSocketHandler serverSocketHandler) {
        this.serverSocketHandler = serverSocketHandler;
    }

    public void getDriver() {
        // hits the matching engine, returns the possible drivers, requests them all until there is a match
    }

    public void acceptRider () {

    }

}
