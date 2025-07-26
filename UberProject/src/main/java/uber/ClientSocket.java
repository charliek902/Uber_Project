package uber;
import java.net.URI;
import java.nio.ByteBuffer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class ClientSocket extends WebSocketClient {
    private User user;

    public ClientSocket(URI serverUri, User user) {
        super(serverUri);
        this.user = user;
    }

    public ClientSocket(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }


    @Override
    public void onMessage(String message) {
        // Pass message to the user
        user.addMessage(message);
    }

    @Override
    public void onMessage(ByteBuffer message) {
        // Pass message to the user
        user.addMessage(message);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

}