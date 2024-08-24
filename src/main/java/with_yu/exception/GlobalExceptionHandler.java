package with_yu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import with_yu.common.response.error.ErrorRes;
import with_yu.common.response.error.ErrorType;

import static with_yu.common.response.error.ErrorType.INTERNAL_SERVER_ERROR;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 커스텀 예외 발생 - ErrorType 참고
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(final CustomException e) {
        ErrorType error = e.getErrorType();
        log.error("Error occured : [errorCode={}, message={}]", error.getStatusCode(), error.getMessage());
        return ResponseEntity.status(error.getStatus()).body(ErrorRes.of(error.getStatusCode(),error.getMessage()));
    }

    // Validation 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleValidationException(final MethodArgumentNotValidException e) {
        Map<String, String> body = new HashMap<>();
        for(FieldError fieldError : e.getBindingResult().getFieldErrors() ){
            body.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error("Error occured : [message={}]",body);
        return ResponseEntity.status(ErrorType.BAD_REQUEST.getStatus()).body(body);
    }

    // 일반 예외 처리
    protected ResponseEntity<?> handleException(final Exception e) {
        ErrorRes error = new ErrorRes(INTERNAL_SERVER_ERROR.getStatusCode(), INTERNAL_SERVER_ERROR.getMessage());
        log.error("Error occured : [errorCode={}, message={}]",error.status(), error.message());
        return ResponseEntity.status(error.status()).body(error);
    }
}
