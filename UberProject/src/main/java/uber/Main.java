package uber;

public class Main {

    public static void main(String[] args)  {
        ThreadPool threads = new ThreadPool();
        ServerSocketHandler serverSocketHandler = new ServerSocketHandler();
        // all services need to run concurrently ...
        LocationService locationService = new LocationService(serverSocketHandler);
        MessageService messageService = new MessageService(serverSocketHandler);
        TripService tripService = new TripService(serverSocketHandler);
        ClientCreatorFactory ccf = new ClientCreatorFactory(locationService, messageService,tripService);




    }




}
