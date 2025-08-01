package uber;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Rider extends User{
    Driver currentDriver;
    Integer size;

    public Rider(Integer id, String fullName, GeoLocation location, String email, LocationService locationService, MessageService messageService, TripService tripService, ConnectionDB db, Integer size) throws Exception {
        super(id, fullName, location, email, locationService, messageService, tripService, db);
        this.size = size;
    }

    public void move() {
        if (this.currentRide == null) {
            this.setCurrentLocation(this.randomizeLocation(this.getCurrentLocation().getLongitude(), this.getCurrentLocation().getLatitude()));
        }
    }

    private GeoLocation randomizeLocation(Integer longitude, Integer latitude) {
        int movementSignLongitude = (int)(Math.random() * 2);
        int movementSignLatitude = (int)(Math.random() * 2);
        int steps = 100;
        Double newLongitude = movementSignLongitude == 1 ? longitude + Math.ceil((Math.random() * steps)) :  longitude + (Math.random() * (steps * -1));
        Double newLatitude = movementSignLatitude == 1 ?  latitude + (Math.random() * steps) :  latitude + (Math.random() * (steps * -1));
        return new GeoLocation(newLongitude.intValue(), newLatitude.intValue());
    }

    @Override
    public void cancelTrip(){
        Request request = new RequestBuilder(new Request())
                .setCurrentUser(this)
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
    public void completeTrip(){
        Request request = new RequestBuilder(new Request())
                .setCurrentUser(this)
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
        if (this.currentRide != null) {
            Request request = new RequestBuilder(new Request())
                    .setStartingLocation(this.currentLocation)
                    .setCurrentUser(this)
                    .setRide(this.getCurrentRide())
                    .setRequestType(RequestType.UPDATE_LOCATION)
                    .setNotification(
                            new NotificationBuilder(new Notification(this.Id, this.currentRide.currentRider.Id))
                                    .setNotificationType(RequestType.UPDATE_LOCATION)
                                    .setNotificationVisibility(false)
                                    .build()
                    )
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
                    System.out.println("messages size rider: " + messagesQueue.size());
                case UPDATE_LOCATION:
                    ConcurrentLinkedQueue<Notification> locationQueue = clientLocation.get(request.notification.sender);
                    locationQueue.add(request.notification);
                    System.out.println("location size rider: " + locationQueue.size());
                case FIND_DRIVERS, CANCEL_TRIP, COMPLETE_TRIP:
                    ConcurrentLinkedQueue<Notification> requestsQueue = requests.get(request.notification.sender);
                    requestsQueue.add(request.notification);
                    System.out.println("WHEKLRNEWKLNSFNLK");

                    System.out.println("body: " + request.notification.body);
                    System.out.println(request.requestType);
                    System.out.println("requests queue rider size: " + requestsQueue.size());
                default:

            }
        }
        return null;
    }

    @Override
    public void sendMessage(String message) {
        if (this.currentRide == null) {
            return;
        }

//        this.messageService.sendMessage(new Message(this.Id, this.currentDriver.Id, message));
    }



    public void getDriver(GeoLocation destination) {
        Request request = new RequestBuilder(new Request())
                .setRequestType(RequestType.FIND_DRIVERS)
                .setNotification(
                        new NotificationBuilder(new Notification(this.Id, null))
                                .setNotificationType(RequestType.FIND_DRIVERS)
                                .setNotificationVisibility(false)
                                .build()
                )
                .setStartingLocation(this.currentLocation)
                .setDestinationLocation(destination)
                .setCurrentUser(this)
                .validate()
                .build();
        Response response = this.locationService.getDriver(request);
        this.setCurrentRide(new Ride(this, response.driver, this.currentLocation, destination));
    }
}
