package sopt.org.hmh.global.auth.jwt;

import static sopt.org.hmh.global.auth.jwt.JwtConstants.ADMIN_ROLE;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;

@RequiredArgsConstructor
@Component
public class JwtValidator {
    private final JwtGenerator jwtGenerator;

    public void validateAccessToken(String accessToken) {
        try {
            parseToken(accessToken);
        } catch (ExpiredJwtException e) {
            throw new JwtException(JwtError.EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw new JwtException(JwtError.INVALID_ACCESS_TOKEN);
        }
    }

    public void validateRefreshToken(String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new JwtException(JwtError.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            throw new JwtException(JwtError.INVALID_REFRESH_TOKEN);
        }
    }

    private Jws<Claims> parseToken(String token) {
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        return jwtParser.parseClaimsJws(token);
    }

    public void validateAdminToken(String token) {
        String subject = parseToken(token).getBody().getSubject();
        if (!subject.equals(ADMIN_ROLE)) {
            throw new JwtException(JwtError.INVALID_ADMIN_TOKEN);
        }
    }
}