package uber;
import java.util.ArrayList;

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

    public void setCurrentRider(Rider rider){
        this.rider = rider;
    }

    public Rider getCurrentRider(){
        return this.rider;
    }


    @Override
    public void cancelTrip(){
        Request request = new RequestBuilder(new Request())
                .setStartingLocation(this.currentRide.getStartingLocation())
                .setDestinationLocation(this.currentRide.getDestinationLocation())
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
    public void completeTrip() {
        Request request = new RequestBuilder(new Request())
                .setStartingLocation(this.currentRide.getStartingLocation())
                .setDestinationLocation(this.currentRide.getDestinationLocation())
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
    public void updateLocation() {
        if(this.currentRide == null) {
            GeoLocation currentLocation = this.getCurrentLocation();
            GeoLocation newLocation = this.randomizeLocation(currentLocation.getLongitude(), currentLocation.getLatitude());
            Request request = new RequestBuilder(new Request())
                    .setStartingLocation(this.getCurrentLocation())
                    .setNewDriverLocation(newLocation)
                    .setCurrentUser(this)
                    .setRequestType(RequestType.UPDATE_LOCATION)
                    .validate()
                    .build();
            Response response = this.locationService.updateLocation(request);
        } else {
            Request request = new RequestBuilder(new Request())
                    .setStartingLocation(this.currentLocation)
                    .setNotification(
                            new NotificationBuilder(new Notification(this.Id, this.currentRide.currentRider.Id))
                            .setNotificationType(RequestType.UPDATE_LOCATION)
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
    public Request processRequests(String jsonMessage) {
        Request request = super.processRequests(jsonMessage);
        return request;
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
        this.updateLocation();
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
