package sopt.org.HMH.domain.user.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.user.User;
import sopt.org.HMH.domain.user.exception.UserError;
import sopt.org.HMH.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User get(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserError.USER_NOT_FOUND.getErrorMessage()));
    }
}
