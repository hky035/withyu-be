package with_yu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import with_yu.common.response.error.ErrorType;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorType errorType;
}
