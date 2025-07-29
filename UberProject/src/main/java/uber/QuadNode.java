package uber;

import java.util.ArrayList;

public class QuadNode {
    ArrayList<Driver> availableDrivers;
    GeoLocation topLeft;
    Integer innerGridHeight;
    ArrayList<QuadNode> quadNodes = new ArrayList<QuadNode>();
    Boolean isLeaf = false;

    public QuadNode(GeoLocation topLeft, Integer innerGridHeight) {
        this.topLeft = topLeft;
        this.innerGridHeight = innerGridHeight;
    }

    public void buildQuadNode() {
        GeoLocation center = new GeoLocation(topLeft.getLongitude() + innerGridHeight, topLeft.getLongitude() + innerGridHeight);
        QuadNode topLeft = new QuadNode(new GeoLocation(center.getLongitude() - innerGridHeight, center.getLatitude() - innerGridHeight), innerGridHeight / 2);
        QuadNode topRight = new QuadNode(new GeoLocation(center.getLongitude() - innerGridHeight, center.getLatitude()), innerGridHeight / 2);
        QuadNode bottomLeft = new QuadNode(new GeoLocation(center.getLongitude(), center.getLatitude() - innerGridHeight), innerGridHeight / 2);
        QuadNode bottomRight = new QuadNode(new GeoLocation(center.getLongitude(), center.getLatitude()), innerGridHeight / 2);
        quadNodes.add(topLeft);
        quadNodes.add(topRight);
        quadNodes.add(bottomLeft);
        quadNodes.add(bottomRight);

        for (QuadNode quadNode : quadNodes) {
            if (this.innerGridHeight > 1) {
                quadNode.buildQuadNode();
            } else {
                quadNode.setIsLeaf();
            }
        }
    }

    public void setIsLeaf() {
        this.isLeaf = true;
    }

    public Boolean checkLocationInNode(GeoLocation location) {
        for (QuadNode quadNode : quadNodes) {

        }
        return true;
    }

    public void addDriver(Driver driver) {
        if (driver.getUserStatus().equals(UserStatus.UNAVAILABLE)) {
            return;
        }

        if(this.isLeaf && driver.userStatus == UserStatus.AVAILABLE) {
            this.availableDrivers.add(driver);
            driver.setUserStatus(UserStatus.UNAVAILABLE);
            return;
        }
        for (QuadNode quadNode : quadNodes) {
            if (quadNode.checkLocationInNode(driver.getCurrentLocation())) {
                quadNode.addDriver(driver);
                if (driver.getUserStatus().equals(UserStatus.UNAVAILABLE)) {
                    return;
                }
            }
        }
    }

    public ArrayList<Driver> findDrivers(Rider rider, Integer riderRadius, ArrayList<Driver> foundDrivers) {
        return new ArrayList<Driver>();
    }

}
