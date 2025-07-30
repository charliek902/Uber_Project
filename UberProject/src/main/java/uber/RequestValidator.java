package uber;

import java.util.ArrayList;

public class RequestValidator {

    public static ArrayList<String> validateRequest(Request request) {
        ArrayList<String> missingFields = new ArrayList<String>();
        switch (request.requestType){
            case UPDATE_LOCATION:
                return validateUpdateLocation(request, missingFields);
            case FIND_DRIVERS:
                return validateRequestDrivers(request, missingFields);
            case CANCEL_TRIP:
                return validateEndTrip(request, missingFields);
            case COMPLETE_TRIP:
                return validateEndTrip(request, missingFields);
            case SEND_MESSAGE:
                return validateSendMessage(request, missingFields);
            case ACCEPT_RIDER:
                return validAcceptRider(request, missingFields);
            default:
                missingFields.add("request type");
                return missingFields;
        }
    }

    private static ArrayList<String> validateUpdateLocation(Request request, ArrayList<String> missingFields) {
        return missingFields;
    }

    private static ArrayList<String> validateRequestDrivers(Request request, ArrayList<String> missingFields) {
        return missingFields;
    }

    private static ArrayList<String> validateEndTrip(Request request, ArrayList<String> missingFields) {
        return missingFields;
    }

    private static ArrayList<String> validateSendMessage(Request request, ArrayList<String> missingFields) {
        return missingFields;
    }

    private static ArrayList<String> validAcceptRider(Request request, ArrayList<String> missingFields) {
        return missingFields;
    }

}
