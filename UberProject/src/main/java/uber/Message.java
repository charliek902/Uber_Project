package uber;

public class Message {

    Integer sender;
    Integer receiver;
    String message;
    Boolean seen;

    public Message(Integer sender, Integer receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.seen = false;
    }

    public Boolean getSeen(){
        return this.seen;
    }

    public void setSeen(){
        this.seen = true;
    }

    public void setNotificationBody(){

    }


}
