package sopt.org.hmh.global.auth.jwt;

import static sopt.org.hmh.global.auth.jwt.JwtPrefixExtractor.extractPrefix;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.auth.dto.response.ReissueResponse;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;

    @Transactional
    public ReissueResponse reissueToken(String refreshToken) {
        String parsedRefreshToken = extractPrefix(refreshToken);
        String userId = jwtProvider.getSubject(parsedRefreshToken);
        jwtValidator.validateRefreshToken(parsedRefreshToken);
        return ReissueResponse.of(jwtProvider.issueToken(userId));
    }

    public TokenResponse issueToken(String subjectId) {
        return jwtProvider.issueToken(subjectId);
    }

    public String issueAdminToken() {
        return jwtProvider.issueAdminToken();
    }

    public void validateAdminToken(String token){
        jwtValidator.validateAdminToken(token);
    }

}
