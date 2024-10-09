package with_yu.common.response.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효성 검증 실패. 잘못된 요청입니다."),
    MAIL_SEND_FAILED(HttpStatus.BAD_REQUEST, "이메일 발송에 실패하였습니다."),


    // 401
    UN_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "인증되지 않은 토큰입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 실패입니다."),
    SOCIAL_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "소셜 로그인 실패입니다."),

    // 인가
    // 403
    UN_AUTHORIZATION(HttpStatus.FORBIDDEN, "허용되지 않은 접근입니다."),

    // 데이터
    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    CARPOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카풀입니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다."),

    // 충돌
    // 409
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    EMAIL_ALREADY_USED(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    NICKNAME_ALREADY_USED(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    PHONE_UMBER_ALREADY_USED(HttpStatus.CONFLICT, "이미 사용 중인 전화번호 입니다."),

    // 서버 에러
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러. 서버 팀으로 연락주시기 바랍니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode(){
        return this.status.value();
    }


}
