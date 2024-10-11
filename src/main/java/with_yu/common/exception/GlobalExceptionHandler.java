package with_yu.common.exception;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(final CustomException e) {
        ErrorType error = e.getErrorType();
        log.error("Error occurred : [errorCode={}, message={}]", error.getStatusCode(), error.getMessage());
        return ResponseEntity.status(error.getStatus()).body(ErrorRes.of(error.getStatusCode(),error.getMessage()));
    }

    // Validation 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleValidationException(final MethodArgumentNotValidException e) {
        Map<String, String> body = new HashMap<>();
        for(FieldError fieldError : e.getBindingResult().getFieldErrors() ){
            body.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error("Error occurred : [message={}]",body);
        return ResponseEntity.status(ErrorType.BAD_REQUEST.getStatus()).body(body);
    }

    // 일반 예외 처리
    @ExceptionHandler
    protected ResponseEntity<?> handleException(final Exception e) {
        ErrorRes error = new ErrorRes(INTERNAL_SERVER_ERROR.getStatusCode(), INTERNAL_SERVER_ERROR.getMessage());
        log.error("Error occurred : [errorCode={}, message={}]\n<<Stack Trace 5 lines>>\n{}",e.getClass(), e.getMessage(), getStackTrace(e));
        return ResponseEntity.status(error.status()).body(error);
    }

    private String getStackTrace(final Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(stackTrace.length, 5); i++) {
            sb.append(stackTrace[i].toString()).append("\n");
        }
        return sb.toString();
    }
}
