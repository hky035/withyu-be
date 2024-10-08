package with_yu.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import with_yu.common.response.success.SuccessRes;
import with_yu.common.response.success.SuccessType;
import with_yu.common.util.CookieUtil;
import with_yu.domain.auth.dto.JwtTokenDto;
import with_yu.domain.auth.dto.LoginReq;
import with_yu.domain.auth.dto.SignupReq;
import with_yu.domain.auth.service.AuthService;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignupReq.General req){
        authService.signUp(req);
        return generalResponse(SuccessType.USER_CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq.General req){
        return successAuthResponse(SuccessType.LOGIN_SUCCESS, authService.login(req));
    }



    private ResponseEntity<?> generalResponse(SuccessType successType){
        return ResponseEntity.status(successType.getStatus())
                .body(SuccessRes.from(successType));
    }

    private ResponseEntity<?> successAuthResponse(SuccessType successType, JwtTokenDto jwtTokenDto){
        return ResponseEntity.status(successType.getStatus())
                .header(HttpHeaders.AUTHORIZATION, jwtTokenDto.accessToken())
                .header(HttpHeaders.SET_COOKIE, cookieUtil.createCookie("refreshToken", jwtTokenDto.refreshToken()).toString())
                .body(SuccessRes.from(successType));
    }

}
