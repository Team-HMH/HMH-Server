package sopt.org.hmh.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.users.dto.response.UserInfoResponse;
import sopt.org.hmh.domain.users.repository.UserRepository;
import sopt.org.hmh.global.auth.redis.TokenService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Transactional
    public void withdraw(Long userId) {
        tokenService.deleteRefreshToken(userId);
        userRepository.findByIdOrThrowException(userId).softDelete();
    }

    public void logout(Long userId) {
        tokenService.deleteRefreshToken(userId);
    }

    public UserInfoResponse getUserInfo(Long userId) {
        return UserInfoResponse.of(userRepository.findByIdOrThrowException(userId));
    }

}
