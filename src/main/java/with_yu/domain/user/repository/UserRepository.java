package with_yu.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import with_yu.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNickname(String nickname);
}
