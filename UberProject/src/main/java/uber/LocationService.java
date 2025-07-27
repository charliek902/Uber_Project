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
        Boolean riderWithNoRide = request.currentUser instanceof Rider && request.ride == null;

        if (driverWithNoRide) {
            ArrayList<Driver> drivers = new ArrayList<Driver>();
            drivers.add(request.driver);
            this.matchingEngine.addDrivers(drivers);
            return new ResponseBuilder(new Response())
                    .setStatus(Status.SUCCESS)
                    .build();
        } else if (riderWithNoRide) {
            ArrayList<Rider> riders = new ArrayList<Rider>();
            riders.add(request.rider);
            this.matchingEngine.addRiders(riders);
            return new ResponseBuilder(new Response())
                    .setStatus(Status.SUCCESS)
                    .build();
        }
        return this.serverSocketHandler.sendMessageFromClient(request);
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

    public Response getDriver(Request request) {
        request = new RequestBuilder(request)
                .setMatchingDrivers(matchingEngine.getAvailableDrivers(request.start))
                .build();
        Response response = serverSocketHandler.sendMessageFromClient(request);
        if (response.status != null && response.status.equals(Status.SUCCESS)) {
            return response;
        }
        return Utils.returnFailure();
    }

}
