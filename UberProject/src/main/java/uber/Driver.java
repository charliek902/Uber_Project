package uber;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Driver extends User {
    public Integer size;
    public Rider rider;
    public Boolean isLocked;

    // USE THE STATE PATTERN FOR MOVEMENT

    public Driver(Integer id, String fullName, GeoLocation location, String email, LocationService locationService, MessageService messageService, TripService tripService, ConnectionDB db, Integer size) throws Exception {
        super(id, fullName, location, email, locationService, messageService, tripService, db);
        this.size = size;
        this.isLocked = false;
    }



    @Override
    public void cancelTrip(){
        Request request = new RequestBuilder(new Request())
                .setStartingLocation(this.currentRide.getStartingLocation())
                .setDestinationLocation(this.currentRide.getDestinationLocation())
                .setCurrentUser(this)
                .setNotification(
                        new NotificationBuilder(new Notification(this.Id, this.currentRide.currentRider.Id))
                                .setNotificationType(RequestType.CANCEL_TRIP)
                                .setNotificationVisibility(false)
                                .build()
                )
                .setRide(this.currentRide)
                .setCurrentRequestTime(0)
                .setTimeOut(300)
                .setSize(this.size)
                .setRequestType(RequestType.CANCEL_TRIP)
                .validate()
                .build();
        this.tripService.endTrip(request);
    }

    @Override
    public void completeTrip() {
        Request request = new RequestBuilder(new Request())
                .setStartingLocation(this.currentRide.getStartingLocation())
                .setDestinationLocation(this.currentRide.getDestinationLocation())
                .setCurrentUser(this)
                .setNotification(
                        new NotificationBuilder(new Notification(this.Id, this.currentRide.currentRider.Id))
                                .setNotificationType(RequestType.COMPLETE_TRIP)
                                .setNotificationVisibility(false)
                                .build()
                )
                .setRide(this.currentRide)
                .setCurrentRequestTime(0)
                .setTimeOut(300)
                .setSize(this.size)
                .setRequestType(RequestType.COMPLETE_TRIP)
                .validate()
                .build();
        this.tripService.endTrip(request);
    }

    @Override
    public void updateLocation(GeoLocation oldLocation, GeoLocation newLocation) {
        if(this.currentRide == null) {
            Request request = new RequestBuilder(new Request())
                    .setPastLocation(this.getCurrentLocation())
                    .setNewLocation(newLocation)
                    .setCurrentUser(this)
                    .setRequestType(RequestType.UPDATE_LOCATION)
                    .validate()
                    .build();
            Response response = this.locationService.updateLocation(request);
        } else {
            Request request = new RequestBuilder(new Request())
                    .setPastLocation(this.getCurrentLocation())
                    .setNewLocation(newLocation)
                    .setNotification(
                            new NotificationBuilder(new Notification(this.Id, this.currentRide.currentRider.Id))
                            .setNotificationType(RequestType.UPDATE_LOCATION)
                            .setNotificationVisibility(false)
                            .build()
                    )
                    .setCurrentUser(this)
                    .setRide(this.currentRide)
                    .setRequestType(RequestType.UPDATE_LOCATION)
                    .validate()
                    .build();
            Response response = this.locationService.updateLocation(request);
        }
    }

    @Override
    public Request handleIncomingRequests(String jsonMessage) {
        Request request = super.handleIncomingRequests(jsonMessage);
        if(request != null) {
            switch (request.requestType) {
                case SEND_MESSAGE:
                    ConcurrentLinkedQueue<Notification> messagesQueue = clientMessages.get(request.notification.sender);
                    messagesQueue.add(request.notification);
                    System.out.println("messages size: " + messagesQueue.size());
                case UPDATE_LOCATION:
                    ConcurrentLinkedQueue<Notification> locationQueue = clientLocation.get(request.notification.sender);
                    locationQueue.add(request.notification);
                case CANCEL_TRIP, COMPLETE_TRIP, ACCEPT_RIDER:
                    ConcurrentLinkedQueue<Notification> requestsQueue = requests.get(request.notification.sender);
                    requestsQueue.add(request.notification);
            }
        }
        return null;
    }

    @Override
    public void sendMessage(String message) {
        if (this.currentRide == null) {
            return;
        }
        Request request = new RequestBuilder(new Request())
                .setStartingLocation(this.currentRide.getStartingLocation())
                .setDestinationLocation(this.currentRide.getDestinationLocation())
                .setNotification(
                        new NotificationBuilder(new Notification(this.Id, this.currentRide.currentRider.Id))
                                .setNotificationType(RequestType.SEND_MESSAGE)
                                .setNotificationBody(message)
                                .setNotificationVisibility(false)
                                .build()
                )
                .setCurrentUser(this)
                .setRide(this.currentRide)
                .setCurrentRequestTime(0)
                .setTimeOut(300)
                .setSize(this.size)
                .setRequestType(RequestType.SEND_MESSAGE)
                .validate()
                .build();
        this.messageService.sendMessage(request);
    }

    public void acceptRider(Rider rider) {
        if(this.rider == null) {
            Request request = new RequestBuilder(new Request())
                    .setCurrentUser(this)
                    .setNotification(
                            new NotificationBuilder(new Notification(this.Id, this.currentRide.currentRider.Id))
                                    .setNotificationType(RequestType.ACCEPT_RIDER)
                                    .setNotificationVisibility(false)
                                    .build()
                    )
                    .setCurrentRequestTime(0)
                    .setTimeOut(300)
                    .setSize(this.size)
                    .setRequestType(RequestType.ACCEPT_RIDER)
                    .validate()
                    .build();
            this.tripService.acceptRider(request);
        }
    }

    public void move() {
        GeoLocation newLocation = new GeoLocation(null, null);
        if(this.currentRide == null) {
            newLocation = this.randomizeLocation(currentLocation.getLongitude(), currentLocation.getLatitude());
        } else {
            if (this.currentLocation.getLongitude() == this.currentRide.destinationLocation.getLongitude()) {
                newLocation.setLongitude(this.currentLocation.getLongitude());
            } else if (this.currentLocation.getLongitude() > this.currentRide.destinationLocation.getLongitude()) {
                newLocation.setLongitude(this.currentLocation.getLongitude() - 1);
            } else {
                newLocation.setLongitude(this.currentLocation.getLongitude() + 1);
            }
            if (this.currentLocation.getLatitude() == this.currentRide.destinationLocation.getLatitude()) {
                newLocation.setLongitude(this.currentLocation.getLatitude());
            } else if (this.currentLocation.getLatitude() > this.currentRide.destinationLocation.getLatitude()) {
                newLocation.setLongitude(this.currentLocation.getLatitude() - 1);
            } else {
                newLocation.setLongitude(this.currentLocation.getLatitude() + 1);
            }
        }
        this.updateLocation(this.currentLocation, newLocation);
    }

    private GeoLocation randomizeLocation(Integer longitude, Integer latitude) {
        int movementSignLongitude = (int)(Math.random() * 2);
        int movementSignLatitude = (int)(Math.random() * 2);
        int steps = 100;
        Double newLongitude = movementSignLongitude == 1 ? longitude + Math.ceil((Math.random() * steps)) :  longitude + (Math.random() * (steps * -1));
        Double newLatitude = movementSignLatitude == 1 ?  latitude + (Math.random() * steps) :  latitude + (Math.random() * (steps * -1));
        return new GeoLocation(newLongitude.intValue(), newLatitude.intValue());
    }

}
