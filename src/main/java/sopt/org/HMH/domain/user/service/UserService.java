package sopt.org.HMH.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.user.User;
import sopt.org.HMH.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findByIdOrThrowException(userId);
    }
}
