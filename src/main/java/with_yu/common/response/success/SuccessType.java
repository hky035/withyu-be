package with_yu.common.response.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessType {

    // 200
    OK(HttpStatus.OK, "요청이 정상적으로 완료되었습니다."),
    REGISTRATION_SUCCESS(HttpStatus.OK, "카풀 신청에 성공하였습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),

    // 201
    CREATED(HttpStatus.CREATED, "등록을 성공하였습니다."),
    USER_CREATED(HttpStatus.CREATED, "회원가입에 성공하였습니다."),
    CARPOOL_CREATED(HttpStatus.CREATED, "카풀 등록을 성공하였습니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode(){
        return this.status.value();
    }
}
