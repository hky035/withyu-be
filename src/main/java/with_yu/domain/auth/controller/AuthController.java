package with_yu.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import with_yu.common.response.success.SuccessRes;
import with_yu.common.response.success.SuccessType;
import with_yu.domain.auth.dto.LoginReq;
import with_yu.domain.auth.dto.SignupReq;
import with_yu.domain.auth.service.AuthService;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignupReq.General req){
        authService.signUp(req);
        return generalResponse(SuccessType.USER_CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq.General req){
        authService.login(req);
        return generalResponse(SuccessType.LOGIN_SUCCESS);
    }

    private ResponseEntity<?> generalResponse(SuccessType successType){
        return ResponseEntity.status(successType.getStatus())
                .body(SuccessRes.from(successType));
    }

}
