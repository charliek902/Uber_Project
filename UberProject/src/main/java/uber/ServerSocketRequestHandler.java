package uber;
import org.java_websocket.server.WebSocketServer;

public class ServerSocketRequestHandler {

    public static Response handleUpdateLocation(Request request) {
//        String json = String.format('',)
        request.connectedUser.client.onMessage("blah");

        return Utils.returnFailure();
    }

    public static Response handleRequestDrivers(Request request, ConnectionDB db) {
        for (Integer matchingDriverId : request.matchingDriverIds) {
            ClientSocket socket = db.getUserConnection(matchingDriverId);
            socket.onMessage("");
            // check if the associated driver has accepted
            Driver selectedDriver = (Driver) db.getUserConnection(matchingDriverId).getUser();
            if(selectedDriver != null && selectedDriver.Id == matchingDriverId) {
                return new ResponseBuilder(new Response())
                        .setStatus(Status.SUCCESS)
                        .setDriver(selectedDriver)
                        .build();
            }
        }
        return Utils.returnFailure();
    }

    public static Response handleRequestRiders(Request request, ConnectionDB db) {

        for (Integer matchingRiderId : request.matchingRiderIds) {
            ClientSocket socket = db.getUserConnection(matchingRiderId);
            socket.onMessage("");
            // check if the associated rider has accepted
            Rider selectedRider = (Rider) db.getUserConnection(matchingRiderId).getUser();
            if(selectedRider != null && selectedRider.Id == matchingRiderId) {
                return new ResponseBuilder(new Response())
                        .setStatus(Status.SUCCESS)
                        .setRider(selectedRider)
                        .build();
            }
        }
        return Utils.returnFailure();
    }

    public static Response handleCancelTrip(Request request) {
        return Utils.returnFailure();
    }

    public static Response handleCompleteTrip(Request request) {
        return Utils.returnFailure();
    }

    public static Response handleSendMessage(Request request) {
        return Utils.returnFailure();
    }


    private String buildJSONString() {
        return "";
    }

}
