package uber;

public class RideRequestBuilder {

    RideRequest request;

    public RideRequestBuilder(RideRequest request){
        this.request = request;
    }

    public RideRequestBuilder setSize(Integer size){
        this.request.size = size;
        return this;
    }

    public RideRequestBuilder setStartingLocation(GeoLocation location){
        this.request.start = location;
        return this;
    }

    public RideRequestBuilder setDestinationLocation(GeoLocation location){
        this.request.destination = location;
        return this;
    }

    public RideRequestBuilder setSpecialRequirements(String reqs){
        this.request.requirements = reqs;
        return this;
    }

    public RideRequestBuilder setRideType(Trip tripType){
        request.rideType = tripType;
        return this;}

    public RideRequestBuilder setRider(Rider rider){
        this.request.rider = rider;
        return this;
    }

    public RideRequestBuilder setDriver(Driver driver){
        this.request.driver = driver;
        return this;
    }

    public RideRequestBuilder setRequestType(RequestType type){
        this.request.requestType = type;
        return this;
    }

    public RideRequest build(){
        return this.request;
    }

}
