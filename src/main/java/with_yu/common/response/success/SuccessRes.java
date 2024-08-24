package with_yu.common.response.success;

public record SuccessRes<T>(
        int status,
        String message,
        T data
) {
    public static SuccessRes<?> from(SuccessType success){
        return new SuccessRes<>(success.getStatusCode(), success.getMessage(), null);
    }

    public static <T> SuccessRes<?> from(T data){
        return new SuccessRes<>(SuccessType.OK.getStatusCode(), SuccessType.OK.getMessage(), data);
    }

    public static <T> SuccessRes<?> from(SuccessType success, T data){
        return new SuccessRes<>(success.getStatusCode(), success.getMessage(), data);
    }

}
