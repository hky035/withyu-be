package with_yu.common.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SuccessRes<T> {

    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @Builder
    private SuccessRes(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static SuccessRes<?> from(SuccessType success){
        return SuccessRes.builder()
                .status(success.getStatusCode())
                .message(success.getMessage())
                .build();
    }

    public static <T> SuccessRes<?> from(T data){
        return SuccessRes.builder()
                .status(SuccessType.OK.getStatusCode())
                .message(SuccessType.OK.getMessage())
                .data(data)
                .build();
    }

    public static <T> SuccessRes<?> of(SuccessType success, T data){
        return SuccessRes.builder()
                .status(success.getStatusCode())
                .message(success.getMessage())
                .data(data)
                .build();
    }

    public static SuccessRes<?> ok(){
        return SuccessRes.builder()
                .status(SuccessType.OK.getStatusCode())
                .message(SuccessType.OK.getMessage())
                .build();
    }

}
