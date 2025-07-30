package uber;

public class GeoLocation {
    Integer longitude;
    Integer latitude;
    public GeoLocation(Integer longitude, Integer latitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLocation(Integer longitude, Integer latitude) {
        if (longitude == null && latitude == null) {
            return;
        }
        if (latitude != null) {
            this.latitude = latitude;
        }
        if(longitude != null) {
            this.longitude = longitude;
        }
    }

    public Integer getLongitude() {
        return this.longitude;
    }

    public Integer getLatitude() {
        return this.latitude;
    }


}
