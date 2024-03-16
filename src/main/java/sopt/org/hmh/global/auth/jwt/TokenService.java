package sopt.org.hmh.global.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.auth.dto.response.ReissueResponse;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;
import sopt.org.hmh.global.auth.redis.RedisManagerService;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final RedisManagerService redisManagerService;

    @Transactional
    public ReissueResponse reissueToken(String refreshToken) {
        refreshToken = parseTokenString(refreshToken);
        Long userId = jwtProvider.getSubject(refreshToken);
        jwtValidator.validateRefreshToken(refreshToken);
        redisManagerService.deleteRefreshToken(userId);
        return ReissueResponse.of(jwtProvider.issueToken(userId));
    }

    private String parseTokenString(String tokenString) {
        String[] parsedTokens = tokenString.split(" ");
        if (parsedTokens.length != 2) {
            throw new JwtException(JwtError.INVALID_TOKEN_HEADER);
        }
        return parsedTokens[1];
    }

    public TokenResponse issueToken(Long userId) {
        return jwtProvider.issueToken(userId);
    }

}
