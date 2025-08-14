package uber;

public class main {

    public static void main(String[] args) throws Exception {
//        ThreadPool threads = new ThreadPool();
        ConnectionDB singleTonConnectionDB = new ConnectionDB();
        ServerSocketLayer serverSocketHandler = new ServerSocketLayer(singleTonConnectionDB);
        MatchingEngine matchingEngine = new MatchingEngine();
        LocationService locationService = new LocationService(serverSocketHandler, matchingEngine);
        MessageService messageService = new MessageService(serverSocketHandler);
        TripService tripService = new TripService(serverSocketHandler, matchingEngine);
        ClientCreatorFactory ccf = new ClientCreatorFactory(locationService, messageService, tripService, singleTonConnectionDB);

        Driver driver = new Driver(1, null, new GeoLocation(100, 100), null, locationService, messageService, tripService, singleTonConnectionDB, 1);
        Rider rider = new Rider(2, null, new GeoLocation(100, 120), null, locationService, messageService, tripService, singleTonConnectionDB,  1);

        Ride currentRide = new Ride(rider, driver, rider.getCurrentLocation(), driver.getCurrentLocation());
        driver.setCurrentRide(currentRide);
        rider.setCurrentRide(currentRide);
        driver.setUserStatus(UserStatus.UNAVAILABLE);
        rider.setUserStatus(UserStatus.UNAVAILABLE);
//        driver.move();
//        driver.sendMessage("hello");
//        driver.completeTrip();
//        driver.cancelTrip();
    }




}
