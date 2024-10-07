package with_yu.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import with_yu.common.exception.CustomException;
import with_yu.common.response.error.ErrorType;
import with_yu.domain.auth.dto.SignupReq;
import with_yu.domain.user.repository.UserRepository;
import with_yu.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(SignupReq.General request){
        if(!isAllowedSignup(request))
            throw new CustomException(ErrorType.USER_ALREADY_EXIST);

        userService.createUser(request.toEntity(bCryptPasswordEncoder));
    }

    @Transactional(readOnly = true)
    protected boolean isAllowedSignup(SignupReq.General request){
        return !userService.isExistNickname(request.nickname())
                && !userService.isExitsByEmail(request.email())
                && !userService.isExistPhoneNumber(request.phoneNumber());
    }
}
