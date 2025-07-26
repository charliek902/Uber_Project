package uber;

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

    public RequestBuilder setDestinationLocation(GeoLocation location){
        this.request.destination = location;
        return this;
    }

    public RequestBuilder setSpecialRequirements(String reqs){
        this.request.requirements = reqs;
        return this;
    }

    public RequestBuilder setRideType(Trip tripType){
        request.rideType = tripType;
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

    public RequestBuilder setRequestTime(Integer time) {
        this.request.time = time;
        return this;
    }

    public Request build(){
        return this.request;
    }

}
