package uber;

import java.util.ArrayList;

public class RequestBuilder {

    Request request;

    public RequestBuilder(Request request){
        this.request = request;
    }

    public RequestBuilder setSize(Integer size){
        this.request.size = size;
        return this;
    }

    public RequestBuilder setStartingLocation(GeoLocation location){
        this.request.start = location;
        return this;
    }

    public RequestBuilder setNewDriverLocation(GeoLocation location) {
        this.request.newDriverLocation = location;
        return this;
    }

    public RequestBuilder setDestinationLocation(GeoLocation location){
        this.request.destination = location;
        return this;
    }

    public RequestBuilder setCurrentUser(User user) {
        this.request.currentUser = user;
        return this;
    }

    public RequestBuilder setRide(Ride ride){
        this.request.ride = ride;
        return this;
    }

    public RequestBuilder setUserClientConnection(ClientSocket socket) {
        this.request.connectedUserSocket = socket;
        return this;
    }


    public RequestBuilder setRider(Rider rider){
        this.request.rider = rider;
        return this;
    }

    public RequestBuilder setDriver(Driver driver){
        this.request.driver = driver;
        return this;
    }

    public RequestBuilder setRequestType(RequestType type){
        this.request.requestType = type;
        return this;
    }

    public RequestBuilder setTimeOut(Integer timeoutLength) {
        this.request.timeout = timeoutLength;
        return this;
    }

    public RequestBuilder setCurrentRequestTime(Integer time) {
        this.request.time = time;
        return this;
    }

    public RequestBuilder setMatchingDrivers(ArrayList<Driver> drivers) {
        this.request.matchingDrivers = drivers;
        return this;
    }

    public RequestBuilder setPostMessage(String postMessage) {
        this.request.postMessage = postMessage;
        return this;
    }

    public RequestBuilder validate() {
        ArrayList<String> missingFields = RequestValidator.validateRequest(this.request);
        if (missingFields.size() != 0) {
            if (this.request.currentUser != null) {
                String errorMessage = "Invalid Request. The following fields are missing: " + String.join(", ", missingFields);
                this.request.currentUser.systemMessages.add(errorMessage);
                this.request.requestType = RequestType.INVALID_REQUEST;
            }
        }
        return this;
    }

    public Request build(){
        return this.request;
    }

}
