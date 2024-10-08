package with_yu.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import with_yu.common.exception.CustomException;
import with_yu.common.response.error.ErrorType;
import with_yu.common.util.JwtUtil;
import with_yu.common.util.PasswordEncoderHelper;
import with_yu.domain.auth.dto.JwtTokenDto;
import with_yu.domain.auth.dto.LoginReq;
import with_yu.domain.auth.dto.SignupReq;
import with_yu.domain.user.entity.User;
import with_yu.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoderHelper passwordEncoderHelper;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(SignupReq.General request){
        if(!isAllowedSignup(request))
            throw new CustomException(ErrorType.USER_ALREADY_EXIST);

        userService.createUser(request.toEntity(passwordEncoderHelper));
    }

    @Transactional(readOnly = true)
    public JwtTokenDto login(LoginReq.General request){
        User user = userService.readUserByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

        if(!isValidToLogin(request, user))
            throw new CustomException(ErrorType.LOGIN_FAILED);

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole());

        return JwtTokenDto.of(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public boolean isAllowedSignup(SignupReq.General request){
        return !userService.isExistNickname(request.nickname())
                && !userService.isExitsByEmail(request.email())
                && !userService.isExistPhoneNumber(request.phoneNumber())
                && !userService.isExitsByStudentNumber(request.studentNumber());
    }

    private boolean isValidToLogin(LoginReq.General request, User user){
        return request.email().equals(user.getEmail())
                && passwordEncoderHelper.matches(request.password(), user.getPassword());
    }
}
