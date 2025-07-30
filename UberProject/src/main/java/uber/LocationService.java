package uber;

import java.util.ArrayList;

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
            request.driver.currentLocation = request.start;
            this.matchingEngine.removeDriver(request.driver);
            request.driver.currentLocation = request.newDriverLocation;
            this.matchingEngine.addDriver(request.driver);

            return new ResponseBuilder(new Response())
                    .setStatus(ResponseStatus.SUCCESS)
                    .build();
        }
        return this.serverSocketHandler.sendMessageFromClient(request);
    }

    public Response getDriver(Request request) {
        request = new RequestBuilder(request)
                .setMatchingDrivers(matchingEngine.getAvailableDrivers(request.rider))
                .build();
        Response response = serverSocketHandler.sendMessageFromClient(request);
        if (response.status != null && response.status.equals(ResponseStatus.SUCCESS)) {
            return response;
        }
        return Utils.returnFailure();
    }

}
