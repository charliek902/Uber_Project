package uber;

public class ServerSocketRequestHandler {

    public static Response handleUpdateLocation(Request request, ConnectionDB db) {
        request.connectedUser.client.onMessage(request.returnJSONParams());
        return Utils.returnFailure();
    }

    public static Response handleRequestDrivers(Request request, ConnectionDB db) {
        for (Driver driver : request.matchingDrivers) {
            ClientSocket socket = db.getUserConnection(driver.Id);
            socket.onMessage(request.returnJSONParams());
            Driver selectedDriver = (Driver) db.getUserConnection(driver.Id).getUser();

            if(selectedDriver != null && selectedDriver.Id == driver.Id) {
                return new ResponseBuilder(new Response())
                        .setStatus(ResponseStatus.SUCCESS)
                        .setDriver(selectedDriver)
                        .build();
            }
        }
        return Utils.returnFailure();
    }

    public static Response endTrip(Request request, ConnectionDB db) {
        ClientSocket socket;
        if (request.currentUser instanceof Driver) {
            socket = db.getUserConnection(request.rider.Id);
        } else {
            socket = db.getUserConnection(request.driver.Id);
        }

        socket.onMessage(request.returnJSONParams());

        if (request.currentUser instanceof Driver) {
            request.driver.setCurrentRide(null);
            Rider selectedRider = (Rider) db.getUserConnection(request.rider.Id).getUser();
            if(selectedRider != null && selectedRider.userStatus == UserStatus.FINISHED) {
                return new ResponseBuilder(new Response())
                        .setStatus(ResponseStatus.SUCCESS)
                        .setRider(selectedRider)
                        .build();
            }
            return Utils.returnFailure();
        } else {
            request.rider.setCurrentRide(null);
            Driver selectedDriver = (Driver) db.getUserConnection(request.driver.Id).getUser();
            if(selectedDriver != null && selectedDriver.userStatus == UserStatus.FINISHED) {
                return new ResponseBuilder(new Response())
                        .setStatus(ResponseStatus.SUCCESS)
                        .setDriver(selectedDriver)
                        .build();
            }
            return Utils.returnFailure();
        }
    }


    public static Response handleSendMessage(Request request, ConnectionDB db) {
        if(request.currentUser instanceof Driver) {
            request.rider.client.onMessage(request.returnJSONParams());
        } else {
            request.driver.client.onMessage(request.returnJSONParams());
        }
        return Utils.returnFailure();
    }

    public static Response handleAcceptRider(Request request, ConnectionDB db) {
        request.connectedUser.client.onMessage(request.returnJSONParams());
        // checks on whether user has been accepted (or if another driver got them)s
        return Utils.returnFailure();
    }

}
