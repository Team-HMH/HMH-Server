package sopt.org.hmh.global.auth.jwt;

import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtGenerator jwtGenerator;

    public TokenResponse issueToken(String subjectId) {
        return new TokenResponse(jwtGenerator.generateToken(subjectId, false),
                jwtGenerator.generateToken(subjectId, true));
    }

    public String getSubject(String token) {
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        return jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String issueAdminToken() {
        return jwtGenerator.generateAdminToken();
    }
}