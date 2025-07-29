package uber;

public class ServerSocketRequestHandler {

    public static Response handleUpdateLocation(Request request) {
//        String json = String.format('',)
        request.connectedUser.client.onMessage("blah");

        return Utils.returnFailure();
    }

    public static Response handleRequestDrivers(Request request, ConnectionDB db) {
        for (Driver driver : request.matchingDrivers) {
            ClientSocket socket = db.getUserConnection(driver.Id);
            // TODO: check if the associated driver has accepted
            socket.onMessage("");
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
