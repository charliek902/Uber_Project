package uber;

// this class will be the quadtree - return a list of the nearest MAX top 10 drivers (only drivers which are available will be in the mathcing engine)
// here is where we insert and update and query by quadTrees , when we find a new match / mathces we return

import java.util.ArrayList;

public class MatchingEngine {

    public ArrayList<Driver> drivers;
    public ArrayList<Rider> riders;

    public MatchingEngine() {
        // we cannot need to implement the actual quadtree
    }


    public void addDrivers(ArrayList<Driver> drivers) {
        this.drivers.addAll(drivers);
    }

    public void addRiders(ArrayList<Rider> riders) {
        this.riders.addAll(riders);
    }

    public ArrayList<Integer> getAvailableDrivers(GeoLocation startingLocation) {
        ArrayList<Integer> riderIds = new ArrayList<Integer>();
        riderIds.add(1);
        return riderIds;
    }

    public ArrayList<Integer> getAvailableRiders(GeoLocation startingLocation) {
        ArrayList<Integer> riderIds = new ArrayList<Integer>();
        riderIds.add(2);
        return riderIds;
    }




}
