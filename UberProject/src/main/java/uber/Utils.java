package uber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class Utils {

    private static Object JsonMethod;

    public static Response returnFailure() {
        return new ResponseBuilder(new Response())
                .setStatus(ResponseStatus.FAILURE)
                .build();
    }

    public static String jsonSerialize(Object obj) {
        try{
            ObjectMapper mapper = new ObjectMapper();
//            ObjectMapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
            return mapper.writeValueAsString(obj);
        } catch(JsonProcessingException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static Request jsonDeserialize(String json) {
        try{
            ObjectMapper mapper = new ObjectMapper();
//            ObjectMapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
            return mapper.readValue(json, Request.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return new Request();
        }
    }
}
