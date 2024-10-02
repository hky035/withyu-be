package with_yu.common.response.error;

public record ErrorRes(
        int status,
        String message
) {
    public static ErrorRes of(int status, String message){
        return new ErrorRes(status, message);
    }

    public static ErrorRes from(ErrorType errorType){
        return new ErrorRes(errorType.getStatusCode(), errorType.getMessage());
    }
}
