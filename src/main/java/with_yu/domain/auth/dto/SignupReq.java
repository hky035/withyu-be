package with_yu.domain.auth.dto;

import org.springframework.security.crypto.password.PasswordEncoder;
import with_yu.domain.user.entity.Role;
import with_yu.domain.user.entity.User;

public class SignupReq {

    public record General(
            String name,
            String nickname,
            String email,
            String password,
            String department,
            String phoneNumber,
            int studentNumber
    ) {
        public User toEntity(PasswordEncoder passwordEncoder) {
            return User.builder()
                    .name(name)
                    .nickname(nickname)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .department(department)
                    .phoneNumber(phoneNumber)
                    .studentNumber(studentNumber)
                    .role(Role.USER)
                    .build();
        }
    }

   /* 미사용
   @Builder
    public record SignupCheckInfo(
            String nickname,
            String email,
            String phoneNumber,
            int studentNumber
    ){
        public SignupCheckInfo from(SignupReq.General general) {
            return SignupCheckInfo.builder()
                    .nickname(general.nickname())
                    .email(general.email())
                    .phoneNumber(general.phoneNumber())
                    .studentNumber(studentNumber())
                    .build();
        }
    }*/
}
