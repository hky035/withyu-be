package with_yu.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private String nickname;
    private String email;
    private String password;
    private String department;
    private String phoneNumber;
    private int studentNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String name, String nickname, String email, String password, String department, String phoneNumber, int studentNumber, Role role) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.studentNumber = studentNumber;
        this.role = Role.USER;
    }

    // Provider
    // 차량
    // 받은 리뷰
    // 남긴 리뷰
    // 제공 카풀
    // 받은 카풀

}
