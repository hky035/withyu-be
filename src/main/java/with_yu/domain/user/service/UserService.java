package with_yu.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import with_yu.domain.user.entity.User;
import with_yu.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean isExistNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public boolean isExistPhoneNumber(String phoneNumber){
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional(readOnly = true)
    public boolean isExitsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}
