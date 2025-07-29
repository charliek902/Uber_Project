package uber;

public class Utils {

    public static Response returnFailure() {
        return new ResponseBuilder(new Response())
                .setStatus(ResponseStatus.FAILURE)
                .build();
    }


}
