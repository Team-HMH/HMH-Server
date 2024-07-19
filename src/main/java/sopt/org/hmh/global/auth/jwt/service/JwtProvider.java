package sopt.org.hmh.global.auth.jwt.service;

import static sopt.org.hmh.global.auth.jwt.service.JwtPrefixExtractor.extractPrefix;

import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sopt.org.hmh.global.auth.jwt.dto.TokenResponse;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtGenerator jwtGenerator;

    public TokenResponse issueToken(String subjectId) {
        return new TokenResponse(jwtGenerator.generateToken(subjectId, false),
                jwtGenerator.generateToken(subjectId, true));
    }

    public String getSubject(String token) {
        String extractedToken = extractPrefix(token);
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        return jwtParser.parseClaimsJws(extractedToken)
                .getBody()
                .getSubject();
    }

    public String issueAdminToken() {
        return jwtGenerator.generateAdminToken();
    }
}