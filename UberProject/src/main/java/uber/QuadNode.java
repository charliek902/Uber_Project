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
            }
            else {
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
            Integer bottomLeftQuadNode = topLeftQuadNode.getLongitude() + this.gridDimension;
            Integer topRightQuadNode = topLeftQuadNode.getLatitude() + this.gridDimension;
            Boolean withinLongitude = topLeftQuadNode.getLongitude() <= location.getLongitude() && location.getLongitude() <= bottomLeftQuadNode;
            Boolean withinLatitude = topLeftQuadNode.getLatitude() <= location.getLatitude() && location.getLatitude() <= topRightQuadNode;
            if (withinLongitude && withinLatitude) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkNodeInRiderRadius(GeoLocation riderCenterLocation, Integer riderRadius) {
        Boolean longitudeOverlap = false;
        Boolean latitudeOverlap = false;
        Boolean riderLatitudeWithin = false;
        Boolean nodeLatitudeWithin = false;
        GeoLocation topLeftRiderRadius = new GeoLocation(riderCenterLocation.getLongitude() - riderRadius, riderCenterLocation.getLatitude() - riderRadius);
        GeoLocation bottomLeftRiderRadius = new GeoLocation(riderCenterLocation.getLongitude() + riderRadius, riderCenterLocation.getLatitude() - riderRadius);
        GeoLocation topRightRiderRadius = new GeoLocation(riderCenterLocation.getLongitude() - riderRadius, riderCenterLocation.getLatitude() + riderRadius);
        GeoLocation bottomRightRiderRadius = new GeoLocation(riderCenterLocation.getLongitude() + riderRadius, riderCenterLocation.getLatitude() + riderRadius);
        QuadNode topLeftQuadNode = quadNodes.get(0);
        QuadNode bottomLeftQuadNode = quadNodes.get(2);
        QuadNode topRightQuadNode = quadNodes.get(1);
        QuadNode bottomRightQuadNode = quadNodes.get(3);


//        not (A.right < B.left or A.left > B.right or
//                A.top < B.bottom or A.bottom > B.top)


        Boolean riderLongitudeWithin = topLeftQuadNode.topLeft.getLongitude() <= topLeftRiderRadius.getLongitude() && topLeftRiderRadius.getLongitude()
                <= topLeftQuadNode.topLeft.getLongitude() + topLeftQuadNode.getGridDimension();
        if(!riderLongitudeWithin) {
            riderLongitudeWithin = topLeftQuadNode.topLeft.getLongitude() <= bottomLeftRiderRadius.getLongitude() && bottomLeftRiderRadius.getLongitude()
                    <= topLeftQuadNode.topLeft.getLongitude() + topLeftQuadNode.getGridDimension();
        }


        Boolean nodeLongitudeWithin = false;

        if(riderLongitudeWithin || nodeLongitudeWithin) {
            longitudeOverlap = true;
        }
        if(riderLatitudeWithin || nodeLatitudeWithin) {
            latitudeOverlap = true;
        }
        return longitudeOverlap && latitudeOverlap;
    }

    public void addDriver(Driver driver) {

        if (driver.getUserStatus().equals(UserStatus.AVAILABLE)) {
            return;
        }

        if(this.isLeaf && driver.userStatus != UserStatus.AVAILABLE) {
            this.availableDrivers = new ArrayList<Driver>();
            System.out.println("adds driver to leaf node of quad tree!");
            this.availableDrivers.add(driver);
            driver.setUserStatus(UserStatus.AVAILABLE);
            return;
        }
        for (QuadNode quadNode : quadNodes) {
            if (quadNode.checkLocationInNode(driver.getCurrentLocation()) || quadNode.isLeaf) {
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
            if (quadNode.checkLocationInNode(driver.getCurrentLocation()) || quadNode.isLeaf) {
                quadNode.removeDriver(driver);
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
        for (QuadNode quadNode : quadNodes) {
            if(quadNode.isLeaf) {
                foundDrivers.addAll(quadNode.getAvailableDrivers());
            } else if(quadNode.checkNodeInRiderRadius(rider.getCurrentLocation(), riderRadius)) {
                foundDrivers = quadNode.findDrivers(rider, riderRadius, foundDrivers);
            }
        }
        return foundDrivers;
    }

    public ArrayList<Driver> getAvailableDrivers() {
        return this.availableDrivers;
    }

    public Integer getGridDimension() {
        return this.gridDimension;
    }

}
