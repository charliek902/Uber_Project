package uber;

public class LocationService {
    ServerSocketLayer serverSocketHandler;
    MatchingEngine matchingEngine;

    public LocationService(ServerSocketLayer serverSocketHandler, MatchingEngine matchingEngine) {
        this.serverSocketHandler = serverSocketHandler;
        this.matchingEngine = matchingEngine;
    }

    public void updateLocation(Request request) {
        // also need to update the engine if driver / rider does not have a client yet

    }

    public Response getRider(Request request) {
        request = new RequestBuilder(request)
                .setMatchingRiders(matchingEngine.getAvailableRiders(request.start))
                .build();
        Response response = serverSocketHandler.sendMessageFromClient(request);
        if (response.status != null && response.status.equals(Status.SUCCESS)) {
            return response;
        }
        return Utils.returnFailure();
    }

    public void getDriver(Request request) {
        request = new RequestBuilder(request)
                .setMatchingDrivers(matchingEngine.getAvailableDrivers(request.start))
                .build();
        serverSocketHandler.sendMessageFromClient(request);
    }

}
