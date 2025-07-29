package uber;
import org.java_websocket.server.WebSocketServer;
import java.net.InetSocketAddress;

public class ServerSocketLayer {
    WebSocketServer server;
    ConnectionDB connectionDB;

    public ServerSocketLayer(ConnectionDB userConnections) {
        try{
            this.server = new ServerSocket(new InetSocketAddress("localhost", 8080));
            this.server.start();
            this.connectionDB = userConnections;
        } catch (Exception e) {
            System.out.println("Web socket failed to run!");
        }
    }

    public Response sendMessageFromClient(Request request) {

        if (request.currentUser != null) {
            request = new RequestBuilder(request)
                    .setUserClientConnection(this.connectionDB.getUserConnection(request.currentUser.Id))
                    .build();
        }

        switch (request.requestType) {
            case UPDATE_LOCATION:
                return ServerSocketRequestHandler.handleUpdateLocation(request);
            case FIND_DRIVERS:
                return ServerSocketRequestHandler.handleRequestDrivers(request, connectionDB);
            case CANCEL_TRIP:
                return ServerSocketRequestHandler.handleCancelTrip(request);
            case COMPLETE_TRIP:
                return ServerSocketRequestHandler.handleCompleteTrip(request);
            case SEND_MESSAGE:
                return ServerSocketRequestHandler.handleSendMessage(request);
            default:
                return new ResponseBuilder(new Response())
                        .setStatus(ResponseStatus.FAILURE)
                        .build();
        }
    }
}
