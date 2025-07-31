package uber;

public class Notification {

    Integer sender;
    Integer receiver;
    Boolean seen;
    String notification;
    RequestType notificationType;

    public Notification(Integer sender, Integer receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }



}
