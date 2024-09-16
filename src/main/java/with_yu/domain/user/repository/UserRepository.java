package with_yu.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import with_yu.domain.user.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByProviderId(String providerId);
}
