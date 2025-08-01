package uber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;

public class Utils {

    public static Response returnFailure() {
        return new ResponseBuilder(new Response())
                .setStatus(ResponseStatus.FAILURE)
                .build();
    }

    public static Response returnSuccess() {
        return new ResponseBuilder(new Response())
                .setStatus(ResponseStatus.SUCCESS)
                .build();
    }

    public static String jsonSerialize(Request obj) {
        try{
            obj = cleanRequestBeforeSerialization(obj);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch(JsonProcessingException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static Request jsonDeserialize(String json) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Request.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return new Request();
        }
    }

    // removes circular objects before serialization
    private static Request cleanRequestBeforeSerialization(Request obj) {
        obj.ride = null;
        obj.currentUser = null;
        obj.connectedUserSocket = null;
        return obj;
    }


}
