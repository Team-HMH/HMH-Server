package sopt.org.hmh.global.auth.jwt;

import static sopt.org.hmh.global.auth.jwt.JwtConstants.ADMIN_ROLE;

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

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.access-token-expiration-time}")
    private Long ACCESS_TOKEN_EXPIRATION_TIME;
    @Value("${jwt.refresh-token-expiration-time}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;
    @Value("${jwt.admin-access-token-expiration-time}")
    private Long ADMIN_ACCESS_TOKEN_EXPIRATION_TIME;

    public String generateToken(String subjectId, boolean isRefreshToken) {
        final Date now = generateNowDate();
        final Date expiration = generateExpirationDate(isRefreshToken, now);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subjectId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateAdminToken() {
        final Date now = generateNowDate();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(ADMIN_ROLE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ADMIN_ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }

    private Date generateNowDate() {
        return new Date();
    }

    private Date generateExpirationDate(boolean isRefreshToken, Date now) {
        return new Date(now.getTime() + calculateExpirationTime(isRefreshToken));
    }

    private SecretKey getSigningKey() {
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

}