package uber;

public class ServerSocketRequestHandler {

    public static Response handleUpdateLocation(Request request, ConnectionDB db) {
        ClientSocket clientSocket = getConnectedUserSocket(request, db);
        if (clientSocket != null) {
            clientSocket.onMessage(Utils.jsonSerialize(request));
            return Utils.returnSuccess();
        } else {
            return Utils.returnFailure();
        }
    }

    public static Response handleRequestDrivers(Request request, ConnectionDB db) {
        for (Driver driver : request.matchingDrivers) {
            ClientSocket socket = db.getUserConnection(driver.Id);

            socket.onMessage(Utils.jsonSerialize(request));
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
        ClientSocket socket = getConnectedUserSocket(request, db);
        if (socket == null) {
            return Utils.returnFailure();
        }
        socket.onMessage(Utils.jsonSerialize(request));

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
        ClientSocket clientSocket = getConnectedUserSocket(request, db);
        if (clientSocket != null) {
            clientSocket.onMessage(Utils.jsonSerialize(request));
            return Utils.returnSuccess();
        } else {
            return Utils.returnFailure();
        }
    }

    public static Response handleAcceptRider(Request request, ConnectionDB db) {
        ClientSocket clientSocket = getConnectedUserSocket(request, db);
        if (clientSocket != null) {
            clientSocket.onMessage(Utils.jsonSerialize(request));
            return Utils.returnSuccess();
        } else {
            return Utils.returnFailure();
        }
    }

    private static ClientSocket getConnectedUserSocket(Request request, ConnectionDB db) {
        if (request != null && request.ride != null) {
            if(request.currentUser instanceof Driver) {
                return db.getUserConnection(request.ride.currentRider.Id);
            } else {
                return db.getUserConnection(request.ride.currentDriver.Id);
            }
        }
        return null;
    }
}
