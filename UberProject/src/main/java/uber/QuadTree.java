package uber;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuadTree {
    ArrayList<QuadNode> quadNodes = new ArrayList<QuadNode>();
    public static final Integer rider_radius = 200;

    public QuadTree(GeoLocation center, Integer QuadNodeBoundary) {
        this.buildQuadTree(center, QuadNodeBoundary);
        this.buildQuadNodes();
    }

    public ArrayList<Driver> findAvailableDrivers(Rider rider) {
        ArrayList<Driver> availableDrivers = new ArrayList<Driver>();
        for (QuadNode quadNode : quadNodes) {
            availableDrivers.addAll(quadNode.findDrivers(rider, rider_radius, new ArrayList<Driver>()));
        }
        return availableDrivers;
    }

    public void addDrivers(ArrayList<Driver> drivers) {
        for (Driver driver : drivers) {
            for (QuadNode quadNode : quadNodes) {
                if (quadNode.checkLocationInNode(driver.getCurrentLocation())) {
                    quadNode.addDriver(driver);
                }
            }
        }
    }

    private void buildQuadTree(GeoLocation center, Integer QuadNodeBoundary) {
        QuadNode topLeft = new QuadNode(new GeoLocation(center.getLongitude() - QuadNodeBoundary, center.getLatitude() - QuadNodeBoundary), QuadNodeBoundary / 2);
        QuadNode topRight = new QuadNode(new GeoLocation(center.getLongitude() - QuadNodeBoundary, center.getLatitude()), QuadNodeBoundary / 2);
        QuadNode bottomLeft = new QuadNode(new GeoLocation(center.getLongitude(), center.getLatitude() - QuadNodeBoundary), QuadNodeBoundary / 2);
        QuadNode bottomRight = new QuadNode(new GeoLocation(center.getLongitude(), center.getLatitude()), QuadNodeBoundary / 2);
        quadNodes.add(topLeft);
        quadNodes.add(topRight);
        quadNodes.add(bottomLeft);
        quadNodes.add(bottomRight);
    }

    private void buildQuadNodes() {
        for (QuadNode quadNode : quadNodes) {
            quadNode.buildQuadNode();
        }
    }

}
