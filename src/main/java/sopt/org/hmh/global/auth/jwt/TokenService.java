package sopt.org.hmh.global.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.auth.dto.response.ReissueResponse;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;

    @Transactional
    public ReissueResponse reissueToken(String refreshToken) {
        String parsedRefreshToken = parseTokenString(refreshToken);
        Long userId = jwtProvider.getSubject(parsedRefreshToken);
        jwtValidator.validateRefreshToken(parsedRefreshToken);
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
