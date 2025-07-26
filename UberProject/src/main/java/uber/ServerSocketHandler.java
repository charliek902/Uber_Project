package uber;
import org.java_websocket.server.WebSocketServer;
import java.net.InetSocketAddress;

public class ServerSocketHandler {
    WebSocketServer server;

    public ServerSocketHandler() {
        try{
            this.server = new ServerSocket(new InetSocketAddress("localhost", 8887));
            this.server.start();
        } catch (Exception e) {
            System.out.println("Web socket failed to run!");
        }
    }

    public void sendMessageFromClient(ClientSocket client, String message) {
        // for this overload we just need to find associated clients...
    }

    public void sendMessageFromClient(ClientSocket client, String message, User connectedClient) {
        // need to iterate over all the connected clients, get the associated driver or rider
        // find that connection and then send the message
    }

}
