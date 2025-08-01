package uber;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.java_websocket.client.WebSocketClient;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

abstract public class User {
    public Integer Id;
    public String fullName;
    public GeoLocation currentLocation;
    public Double rating;
    public HashMap<String, ArrayList<Double>> pastRatings;
    public String email;
    Ride currentRide;
    public LocationService locationService;
    public MessageService messageService;
    public TripService tripService;
    public ConnectionDB db;
    @JsonIgnore
    public ClientSocket client;
    public UserStatus userStatus;
    ThreadPool threadPool;
    private final ReentrantLock lock = new ReentrantLock();

    HashMap<Integer, ConcurrentLinkedQueue<Notification>> clientMessages = new HashMap<Integer, ConcurrentLinkedQueue<Notification>>();
    HashMap<Integer, ConcurrentLinkedQueue<Notification>> clientLocation = new HashMap<Integer, ConcurrentLinkedQueue<Notification>>();
    HashMap<Integer, ConcurrentLinkedQueue<Notification>> requests = new HashMap<Integer, ConcurrentLinkedQueue<Notification>>();
    ConcurrentLinkedQueue<String> systemMessages = new ConcurrentLinkedQueue<>();

    public User(Integer id, String fullName, GeoLocation location, String email, LocationService locationService, MessageService messageService, TripService tripService, ConnectionDB db) throws Exception {
        this.fullName = fullName;
        this.currentLocation = location;
        this.rating = 0.0;
        this.pastRatings = new HashMap<String, ArrayList<Double>>();
        this.email = email;
        this.Id = id;
        this.userStatus = UserStatus.STARTED;
        this.locationService = locationService;
        this.messageService = messageService;
        this.tripService = tripService;
        this.db = db;
        this.createClientWebSocketConection();
    }

    public Double getRating(){
        return this.rating;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFullName(){
        return this.fullName;
    }

    public GeoLocation getCurrentLocation() {
        return this.currentLocation;
    }

    public WebSocketClient getClientSocket() {
        return this.client;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCurrentLocation(GeoLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setCurrentRide(Ride ride){
        this.currentRide = ride;
    }
    public Ride getCurrentRide(){
        if(currentRide == null){
            return null;
        }
        return this.currentRide;
    }

    public void removeUserRating(String email){
        if(this.pastRatings.containsKey(email)){
            this.pastRatings.remove(email);
            this.rating = this.setRating();
        }
    }

    public void addRating(String email, Double rating){
        if(!this.pastRatings.containsKey(email)){
            this.pastRatings.put(email, new ArrayList<Double>());
        }
        this.pastRatings.get(email).add(rating);
        this.rating = this.setRating();
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserStatus getUserStatus() {
        return this.userStatus;
    }

    // update location, get location, request a ride, accept a ride

    public void addMessage(String jsonMessage) {
        this.handleIncomingRequests(jsonMessage);
    }


    public void sendMessage(String message) {}

    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    public void updateLocation(GeoLocation oldLocation, GeoLocation newLocation) {

    }

    public void cancelTrip() {}

    public Request handleIncomingRequests(String jsonMessage) {
        Request request = Utils.jsonDeserialize(jsonMessage);
        if(request == null || request.notification == null || request.notification.sender == null) {
            return null;
        }
        if(!clientMessages.containsKey(request.notification.sender)) {
            clientMessages.put(request.notification.sender, new ConcurrentLinkedQueue<>());
        }
        if(!clientLocation.containsKey(request.notification.sender)) {
            clientLocation.put(request.notification.sender, new ConcurrentLinkedQueue<>());
        }
        if(!requests.containsKey(request.notification.sender)) {
            requests.put(request.notification.sender, new ConcurrentLinkedQueue<>());
        }
        return request;
    }


    public void completeTrip() {

    }

    private Double setRating(){
        Double summedOverall = 0.0;
        Integer count = 0;
        for (ArrayList<Double> ratingsPerCustomer : pastRatings.values()) {
            count += ratingsPerCustomer.size();
            for (Double rating : ratingsPerCustomer) {
                summedOverall += rating;
            }
        }
        return summedOverall / count;
    }

    private void createClientWebSocketConection() throws Exception {

        try {
            System.out.println("!!! before the client socket");
            this.client = new ClientSocket(new URI("ws://localhost:8080"), this);
            client.connectBlocking();
            this.db.addToConnectionDB(Id, this.client);
        } catch(InterruptedException e) {
            System.out.println("!!! hits the catch exceptions");
            throw new Exception("InterruptedException- Client Websocket failed to create");
        } catch (URISyntaxException e) {
            throw new Exception("URISyntaxException- Client Websocket failed to create");
        }
    }
}
