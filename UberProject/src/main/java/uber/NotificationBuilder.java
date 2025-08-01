package uber;

public class NotificationBuilder {
    Notification notification;
    RequestType notificationType;

    public NotificationBuilder(Notification notification) {
        this.notification = notification;
    }

    public NotificationBuilder setNotificationType(RequestType notificationType) {
        this.notification.notificationType = notificationType;
        return this;
    }

    public NotificationBuilder setNotificationBody(Object message) {
        this.notification.body = message;
        return this;
    }

    public NotificationBuilder setNotificationVisibility(Boolean seen) {
        this.notification.seen = seen;
        return this;
    }

    public Notification build() {
        return this.notification;
    }

}
