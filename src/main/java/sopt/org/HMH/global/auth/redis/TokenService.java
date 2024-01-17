package sopt.org.HMH.global.auth.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    /**
     * Refresh 토큰 삭제
     */
    public void deleteRefreshToken(Long userId) {
        if (tokenRepository.existsById(userId)) {
            tokenRepository.deleteById(userId);
        } else {
            throw new JwtException(JwtError.INVALID_REFRESH_TOKEN);
        }
    }
}
