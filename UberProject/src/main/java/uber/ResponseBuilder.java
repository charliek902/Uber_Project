package uber;

public class ResponseBuilder {
    Response response;

    public ResponseBuilder(Response response) {
        this.response = response;
    }

    public ResponseBuilder setStatus(Status status) {
        this.response.status = status;
        return this;
    }

    public ResponseBuilder setRider(Rider rider) {
        this.response.rider = rider;
        return this;
    }

    public ResponseBuilder setDriver(Driver driver) {
        this.response.driver = driver;
        return this;
    }

    public ResponseBuilder setErrorMessage(String errorMessage) {
        this.response.errorMessage = errorMessage;
        return this;
    }


    public Response build() {
        return this.response;
    }

}
