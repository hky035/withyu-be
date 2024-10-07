package with_yu.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import with_yu.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNickname(String nickname);
    boolean existsByStudentNumber(int studentNumber);
}
