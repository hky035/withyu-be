package with_yu.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import with_yu.domain.user.dto.SignupReq;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    UUID id;

    String name;
    String nickname;
    String email;
    String profileImageUrl;
    String providerId;
    Role role;

    String studentNumber;
    String department;
    String phoneNumber;


    @Builder
    public User(String nickname, String email, String providerId, String profileImageUrl, Role role){
        this.nickname = nickname;
        this.email = email;
        this.providerId = providerId;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public void signup(SignupReq signupReq, Role role){
        this.name = signupReq.name();
        this.studentNumber = signupReq.studentNumber();
        this.department = signupReq.department();
        this.phoneNumber = signupReq.phoneNumber();
        this.role = role;
    }

    public void updateEmail(String email){
        this.email = email;
    }



}
