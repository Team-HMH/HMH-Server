package sopt.org.hmh.global.auth.jwt.service;

import static sopt.org.hmh.global.auth.jwt.service.JwtPrefixExtractor.extractPrefix;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.auth.dto.response.ReissueResponse;
import sopt.org.hmh.global.auth.jwt.dto.TokenResponse;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;

    @Transactional
    public ReissueResponse reissueToken(String refreshToken) {
        String userId = jwtProvider.getSubject(refreshToken);
        jwtValidator.validateRefreshToken(refreshToken);
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
