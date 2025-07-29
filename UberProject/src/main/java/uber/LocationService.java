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
            ArrayList<Driver> drivers = new ArrayList<Driver>();
            drivers.add(request.driver);
            this.matchingEngine.addDrivers(drivers);
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
