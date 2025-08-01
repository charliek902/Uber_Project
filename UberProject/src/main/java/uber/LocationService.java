package uber;

public class LocationService {
    ServerSocketLayer serverSocketHandler;
    MatchingEngine matchingEngine;

    public LocationService(ServerSocketLayer serverSocketHandler, MatchingEngine matchingEngine) {
        this.serverSocketHandler = serverSocketHandler;
        this.matchingEngine = matchingEngine;
    }

    public Response updateLocation(Request request) {
        Boolean driverWithNoRide = request.currentUser instanceof Driver && request.ride == null;

        if (driverWithNoRide) {
            request.currentUser.currentLocation = request.pastLocation;
            this.matchingEngine.removeDriver((Driver) request.currentUser);
            request.currentUser.currentLocation = request.newLocation;
            this.matchingEngine.addDriver((Driver) request.currentUser);

            return new ResponseBuilder(new Response())
                    .setStatus(ResponseStatus.SUCCESS)
                    .build();
        }
        return this.serverSocketHandler.sendMessageFromClient(request);
    }

    public Response getDriver(Request request) {
        if (request.currentUser instanceof Rider) {
            request = new RequestBuilder(request)
                    .setMatchingDrivers(matchingEngine.getAvailableDrivers((Rider) request.currentUser))
                    .build();
            Response response = serverSocketHandler.sendMessageFromClient(request);
            if (response.status != null && response.status.equals(ResponseStatus.SUCCESS)) {
                return response;
            }
            return Utils.returnFailure();
        }
        return Utils.returnFailure();
    }

}
