package uber;

public class GeoLocation {
    Double longitude;
    Double latitude;
    public GeoLocation(Double longitude, Double latitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLocation(Double longitude, Double latitude){
        if (longitude == null && latitude == null) {
            return;
        }
        if(longitude == null){
            this.latitude = latitude;
            return;
        }
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }


}
