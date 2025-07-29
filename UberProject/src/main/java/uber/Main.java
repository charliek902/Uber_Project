package uber;

public class Main {

    public static void main(String[] args) throws Exception {
        ThreadPool threads = new ThreadPool();
        ConnectionDB singleTonConnectionDB = new ConnectionDB();
        ServerSocketLayer serverSocketHandler = new ServerSocketLayer(singleTonConnectionDB);
        // all services need to run concurrently ...
        MatchingEngine matchingEngine = new MatchingEngine();
        LocationService locationService = new LocationService(serverSocketHandler, matchingEngine);
        MessageService messageService = new MessageService(serverSocketHandler);
        TripService tripService = new TripService(serverSocketHandler, matchingEngine);
        ClientCreatorFactory ccf = new ClientCreatorFactory(locationService, messageService, tripService, singleTonConnectionDB);

        Driver driver = new Driver(1, null, null, null, locationService, messageService, tripService, 1, Trip.NORMAL, singleTonConnectionDB);
        Rider rider = new Rider(2, null, null, null, locationService, messageService, tripService, singleTonConnectionDB,  1);





    }




}
