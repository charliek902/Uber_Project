package uber;
import java.util.ArrayList;

public class Utils {

    public static Response returnFailure() {
        return new ResponseBuilder(new Response())
                .setStatus(ResponseStatus.FAILURE)
                .build();
    }

    public static Request JSONParser(String JSON) {
        RequestBuilder requestBuilder = new RequestBuilder(new Request());
        Integer index = 1;
        String[] parsedJSON = JSON.split(",");

        // consider using some libraries to parse the JSON... (org.jackson)

        while (index < parsedJSON.length - 1) {
            String[] tokens = parsedJSON[index].split(":");
            if(tokens.length == 2) {
                String key = tokens[0];
                String value = tokens[1];
                if (key.equals("start") || key.equals("destination")) {
                    GeoLocation locationToInsert = new GeoLocation(null, null);
                    index += 1;
                    String[] tokensLocationField1 = parsedJSON[index].split(":");
                    if(parsedJSON[index].equals("longitude")) {
                        locationToInsert.longitude = Integer.valueOf(tokensLocationField1[1]);
                    } else {
                        locationToInsert.latitude = Integer.valueOf(tokensLocationField1[1]);
                    }
                    index += 1;
                    String[] tokensLocationField2 = parsedJSON[index].split(":");
                    if(parsedJSON[index].equals("longitude")) {
                        locationToInsert.longitude = Integer.valueOf(tokensLocationField2[1]);
                    } else {
                        locationToInsert.longitude = Integer.valueOf(tokensLocationField2[1]);
                    }
                    if(key.equals("start")) {
                        requestBuilder.setStartingLocation(locationToInsert);
                    } else {
                        requestBuilder.setDestinationLocation(locationToInsert);
                    }
                    index += 1;
                } else {
                    switch(key) {
                        case "size":
                            requestBuilder.setSize(Integer.valueOf(value));
                        case "requestType":
                            switch (value) {
                                case "UPDATE_LOCATION":
                                    requestBuilder.setRequestType(RequestType.UPDATE_LOCATION);
                                case "FIND_DRIVERS":
                                    requestBuilder.setRequestType(RequestType.FIND_DRIVERS);
                                case "CANCEL_TRIP":
                                    requestBuilder.setRequestType(RequestType.CANCEL_TRIP);
                                case "COMPLETE_TRIP":
                                    requestBuilder.setRequestType(RequestType.COMPLETE_TRIP);
                                case "SEND_MESSAGE":
                                    requestBuilder.setRequestType(RequestType.SEND_MESSAGE);
                                case "ACCEPT_RIDER":
                                    requestBuilder.setRequestType(RequestType.ACCEPT_RIDER);
                            }
                        case "timeout":
                            requestBuilder.setTimeOut(Integer.valueOf(value));
                        case "time":
                            requestBuilder.setCurrentRequestTime(Integer.valueOf(value));
                    }
                }
            }
        }
        return requestBuilder.build();
    }
}
