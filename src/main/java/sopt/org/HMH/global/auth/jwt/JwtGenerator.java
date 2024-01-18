package sopt.org.HMH.global.auth.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sopt.org.HMH.global.auth.redis.RefreshToken;
import sopt.org.HMH.global.auth.redis.TokenRepository;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.access-token-expiration-time}")
    private Integer ACCESS_TOKEN_EXPIRATION_TIME;
    @Value("${jwt.refresh-token-expiration-time}")
    private Integer REFRESH_TOKEN_EXPIRATION_TIME;
    private final TokenRepository tokenRepository;

    public String generateToken(Long userId, boolean isRefreshToken) {
        final Date now = generateNowDate();
        final Date expiration = generateExpirationDate(isRefreshToken, now);

        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();

        if (isRefreshToken) {
            tokenRepository.save(
                    RefreshToken.builder()
                            .userId(userId)
                            .refreshToken(token)
                            .expiration(REFRESH_TOKEN_EXPIRATION_TIME / 1000)
                            .build());
        }
        return token;
    }

    private Date generateNowDate() {
        return new Date();
    }

    private Date generateExpirationDate(boolean isRefreshToken, Date now) {
        return new Date(now.getTime() + calculateExpirationTime(isRefreshToken));
    }

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(encodeSecretKey().getBytes());
    }

    private long calculateExpirationTime(boolean isRefreshToken) {
        if (isRefreshToken) {
            return REFRESH_TOKEN_EXPIRATION_TIME;
        }
        return ACCESS_TOKEN_EXPIRATION_TIME;
    }

    private String encodeSecretKey() {
        return Base64.getEncoder()
                .encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }
}