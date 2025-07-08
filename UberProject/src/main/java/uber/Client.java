package uber;

interface Client {
    public void createConnection();
    public void closeConnection();
    public GeoLocation requestLocation();
    public void updateLocation();
    public void cancelTrip();
    public void completeTrip();
}

