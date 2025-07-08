package uber;

import java.util.ArrayList;

// use a heap to distribute network requests to servers
// when there should be a maximum of 10 on going connections for each server

public class APIGateway {

    ArrayList<Server> servers;

    public APIGateway(){
        this.servers =  new ArrayList<>(10);
    }

//    public Server acceptConnection(){
//
//
//    }
//
//    private

}
