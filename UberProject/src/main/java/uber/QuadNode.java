package uber;

import java.util.ArrayList;

public class QuadNode {
    ArrayList<Driver> availableDrivers;
    GeoLocation topLeft;
    Integer gridDimension;
    Integer quadNodeDimension;
    ArrayList<QuadNode> quadNodes = new ArrayList<QuadNode>();
    Boolean isLeaf = false;

    public QuadNode(GeoLocation topLeft, Integer gridDimension) {
        this.topLeft = topLeft;
        this.gridDimension = gridDimension;
        this.quadNodeDimension = gridDimension / 2;
    }

    public void buildQuadNode() {
        GeoLocation center = new GeoLocation(topLeft.getLongitude() + gridDimension, topLeft.getLongitude() + gridDimension);
        QuadNode topLeft = new QuadNode(new GeoLocation(center.getLongitude() - gridDimension, center.getLatitude() - gridDimension), quadNodeDimension);
        QuadNode topRight = new QuadNode(new GeoLocation(center.getLongitude() - gridDimension, center.getLatitude()), quadNodeDimension);
        QuadNode bottomLeft = new QuadNode(new GeoLocation(center.getLongitude(), center.getLatitude() - gridDimension), quadNodeDimension);
        QuadNode bottomRight = new QuadNode(new GeoLocation(center.getLongitude(), center.getLatitude()), quadNodeDimension);
        quadNodes.add(topLeft);
        quadNodes.add(topRight);
        quadNodes.add(bottomLeft);
        quadNodes.add(bottomRight);

        for (QuadNode quadNode : quadNodes) {
            if (this.gridDimension > 1) {
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
            GeoLocation topLeftQuadNode = quadNode.getQuadNodeTopLeft();
            Integer topLeftOfQuadNodeLongitude = topLeftQuadNode.getLongitude() + this.gridDimension;
            Integer topLeftOfQuadNodeLatitude = topLeftQuadNode.getLatitude() + this.gridDimension;
            Boolean withinLongitude = topLeftQuadNode.getLongitude() <= location.getLongitude() && location.getLongitude() <= topLeftOfQuadNodeLongitude;
            Boolean withinLatitude = topLeftQuadNode.getLatitude() <= location.getLatitude() && location.getLatitude() <= topLeftOfQuadNodeLatitude;
            if (withinLongitude && withinLatitude) {
                return true;
            }
        }
        return false;
    }

    public void addDriver(Driver driver) {
        if (driver.getUserStatus().equals(UserStatus.AVAILABLE)) {
            return;
        }

        if(this.isLeaf && driver.userStatus != UserStatus.AVAILABLE) {
            this.availableDrivers.add(driver);
            driver.setUserStatus(UserStatus.AVAILABLE);
            return;
        }
        for (QuadNode quadNode : quadNodes) {
            if (quadNode.checkLocationInNode(driver.getCurrentLocation())) {
                quadNode.addDriver(driver);
                if (driver.getUserStatus().equals(UserStatus.AVAILABLE)) {
                    return;
                }
            }
        }
    }

    public void removeDriver(Driver driver) {
        if (driver.getUserStatus().equals(UserStatus.UNAVAILABLE)) {
            return;
        }
        if(this.isLeaf && driver.userStatus == UserStatus.AVAILABLE) {
            this.availableDrivers.remove(driver);
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

    public GeoLocation getQuadNodeTopLeft() {
        return this.topLeft;
    }

    public ArrayList<Driver> findDrivers(Rider rider, Integer riderRadius, ArrayList<Driver> foundDrivers) {

        return new ArrayList<Driver>();
    }

}
