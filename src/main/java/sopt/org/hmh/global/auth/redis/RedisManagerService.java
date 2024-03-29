package sopt.org.hmh.global.auth.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;

@Service
@RequiredArgsConstructor
public class RedisManagerService {

    private final TokenRepository tokenRepository;

    public void deleteRefreshToken(Long userId) {
        if (tokenRepository.existsById(userId)) {
            tokenRepository.deleteById(userId);
        } else {
            throw new JwtException(JwtError.INVALID_REFRESH_TOKEN);
        }
    }
}
