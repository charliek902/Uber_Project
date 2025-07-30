package uber;
import java.util.ArrayList;

// this class will be the quadtree - return a list of the nearest MAX top 10 drivers (only drivers which are available will be in the mathcing engine)
// here is where we insert and update and query by quadTrees , when we find a new match / mathces we return


public class MatchingEngine {

    public ArrayList<Driver> drivers;
    public ArrayList<Rider> riders;
    public QuadTree quadTree;

    public MatchingEngine() {
        this.quadTree = new QuadTree(new GeoLocation(0, 0), 1000);
    }

    public void addDrivers(ArrayList<Driver> drivers) {
        this.quadTree.addDrivers(drivers);
    }

    public void addDriver(Driver driver) {
        this.quadTree.addDriver(driver);
    }

    public void removeDriver(Driver driver) {
        this.quadTree.removeDriver(driver);
    }

    public ArrayList<Driver> getAvailableDrivers(Rider rider) {
        return this.quadTree.findAvailableDrivers(rider);
    }

}
