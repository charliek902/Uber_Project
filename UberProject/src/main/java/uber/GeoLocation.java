package uber;

public class GeoLocation {
    public Integer longitude;
    public Integer latitude;

    public GeoLocation() {

    }

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

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }


}
