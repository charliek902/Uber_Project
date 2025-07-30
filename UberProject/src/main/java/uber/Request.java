package uber;
import java.util.ArrayList;

public class Request {
    Integer size;
    GeoLocation start;
    GeoLocation newDriverLocation;
    GeoLocation destination;
    Rider rider;
    Driver driver;
    RequestType requestType;
    Integer time;
    Integer timeout;
    User currentUser;
    User connectedUser;
    ClientSocket connectedUserSocket;
    Ride ride;
    String postMessage;
    ArrayList<Driver> matchingDrivers;
    public Request(){}

    public String returnJSONParams() {
        StringBuilder jsonBuilder = new StringBuilder("{\n");

        if (size != null)
            jsonBuilder.append("  \"size\": ").append(size).append(",\n");

        if (start != null)
            jsonBuilder.append("  \"start\": ").append(",\n");
            jsonBuilder.append("  \"longitude\": ").append(start.getLongitude().toString()).append(",\n");
            jsonBuilder.append("  \"latitude\": ").append(start.getLatitude().toString()).append(",\n");

        if (destination != null)
            jsonBuilder.append("  \"destination\": ").append(",\n");
            jsonBuilder.append("  \"longitude\": ").append(destination.getLongitude().toString()).append(",\n");
            jsonBuilder.append("  \"latitude\": ").append(destination.getLatitude().toString()).append(",\n");

        if (rider != null)
            jsonBuilder.append("  \"rider\": ").append(rider.toJSON()).append(",\n");

        if (driver != null)
            jsonBuilder.append("  \"driver\": ").append(driver.toJSON()).append(",\n");

        if (requestType != null)
            jsonBuilder.append("  \"requestType\": \"").append(requestType.toString()).append("\",\n");

        if (time != null)
            jsonBuilder.append("  \"time\": ").append(time).append(",\n");

        if (timeout != null)
            jsonBuilder.append("  \"timeout\": ").append(timeout).append(",\n");

        if (currentUser != null)
            jsonBuilder.append("  \"currentUser\": ").append(currentUser.toJSON()).append(",\n");

        if (connectedUser != null)
            jsonBuilder.append("  \"connectedUser\": ").append(connectedUser.toJSON()).append(",\n");

        if (ride != null)
            jsonBuilder.append("  \"ride\": ").append(ride.toJSON()).append(",\n");

        if (postMessage != null)
            jsonBuilder.append("  \"postMessage\": \"").append(postMessage).append("\",\n");

        if (matchingDrivers != null && !matchingDrivers.isEmpty()) {
            jsonBuilder.append("  \"matchingDrivers\": [\n");
            for (int i = 0; i < matchingDrivers.size(); i++) {
                jsonBuilder.append("    ").append(matchingDrivers.get(i).toJSON());
                if (i < matchingDrivers.size() - 1) jsonBuilder.append(",");
                jsonBuilder.append("\n");
            }
            jsonBuilder.append("  ]\n");
        }
        return jsonBuilder.toString() + "\n}";
    }

}
