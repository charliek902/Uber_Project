package uber;

import java.util.HashMap;

public class ConnectionDB {
    private HashMap<Integer, ClientSocket> connections;

    public ConnectionDB() {
        this.connections = new HashMap<Integer, ClientSocket>();
    }

    public ClientSocket getUserConnection(Integer id){
        if(connections.containsKey(id)) {
            return this.connections.get(id);
        }
        return null;
    }

    public void addToConnectionDB(Integer Id, ClientSocket connection){
        this.connections.put(Id, connection);
    }

    public void removeFromConnectionDB(Integer Id) {
        this.connections.remove(Id);
    }

}
