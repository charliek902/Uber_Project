package uber;

import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;

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


    }

    public void sendMessageFromServer(String message) {
        this.server.broadcast(message);
    }

}
