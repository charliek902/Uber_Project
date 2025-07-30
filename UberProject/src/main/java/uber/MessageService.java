package uber;

public class MessageService {

    ServerSocketLayer serverSocketHandler;
    public MessageService(ServerSocketLayer serverSocketHandler) {
        this.serverSocketHandler = serverSocketHandler;
    }

    public Response sendMessage(Request request) {
        return this.serverSocketHandler.sendMessageFromClient(request);
    }



}
