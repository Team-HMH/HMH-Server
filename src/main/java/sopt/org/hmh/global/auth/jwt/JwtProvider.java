package sopt.org.hmh.global.auth.jwt;

import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtGenerator jwtGenerator;

    public TokenResponse issueToken(Long userId) {
        return new TokenResponse(jwtGenerator.generateToken(userId, false),
                jwtGenerator.generateToken(userId, true));
    }

    public Long getSubject(String token) {
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        return Long.valueOf(jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    public String issueAdminToken() {
        return jwtGenerator.generateAdminToken();
    }
}