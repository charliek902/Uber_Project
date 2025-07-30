package uber;

public class Message {
    Integer sender;
    Integer receiver;
    String message;
    public Message(Integer sender, Integer receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }
}
