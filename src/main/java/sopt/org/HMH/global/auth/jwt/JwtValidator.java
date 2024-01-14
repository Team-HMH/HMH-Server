package sopt.org.HMH.global.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;

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

    private void parseToken(String token) {
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        jwtParser.parseClaimsJws(token);
    }
}