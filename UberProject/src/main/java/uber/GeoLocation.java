package uber;

public class GeoLocation {
    String longitude;
    String latitude;
    public GeoLocation(String longitude, String latitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoLocation getLocation(){
        return this;
    }

    public void setLocation(String longitude, String latitude){
        if (longitude == null && latitude == null) {
            return;
        }
        if(longitude == null){
            this.latitude = latitude;
            return;
        }
        this.longitude = longitude;
    }
}
