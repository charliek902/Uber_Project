package uber;

public class Notification {

    public Integer sender;
    public Integer receiver;
    public Boolean seen;
    public Object body;
    public RequestType notificationType;

    public Notification() {

    }

    public Notification(Integer sender, Integer receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }



}
