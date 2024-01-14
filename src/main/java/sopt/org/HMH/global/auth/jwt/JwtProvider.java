package sopt.org.HMH.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;
import sopt.org.HMH.global.auth.redis.RefreshToken;
import sopt.org.HMH.global.auth.redis.TokenRepository;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 2L;  // 액세스 토큰 만료 기간 2일
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7 * 2L; // 리프레시 토큰 만료 기간 2주

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    private final TokenRepository tokenRepository;

    @PostConstruct
    protected void init() {
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Access 토큰, Refresh 토큰 발급
     */
    public TokenResponse issueToken(Authentication authentication) {
        return TokenResponse.of(
                        generateAccessToken(authentication),
                        generateRefreshToken(authentication)
        );
    }

    /**
     * Access 토큰 생성
     */
    private String generateAccessToken(Authentication authentication) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME));

        claims.put("userId", authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey())
                .compact();
    }


    private String generateRefreshToken(Authentication authentication) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME));

        claims.put("userId", authentication.getPrincipal());

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey())
                .compact();

        tokenRepository.save(
                RefreshToken.builder()
                        .userId(Long.parseLong(authentication.getPrincipal().toString()))
                        .refreshToken(refreshToken)
                        .expiration(REFRESH_TOKEN_EXPIRATION_TIME.intValue() / 1000)
                        .build());

        return refreshToken;
    }

    /**
     * Access 토큰 검증
     */
    public JwtValidationType validateAccessToken(String accessToken) {
        try {
            final Claims claims = getClaim(accessToken);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    /**
     * Refresh 토큰 검증
     */
    public Long validateRefreshToken(String refreshToken) {
        Long userId = getUserFromJwt(refreshToken);

        // Redis에 해당 Refresh 토큰이 존재하지 않음 = Refresh 토큰 만료라는 뜻이므로
        if (tokenRepository.existsById(userId)) {
            return userId;
        } else {
            throw new JwtException(JwtError.INVALID_REFRESH_TOKEN);
        }
    }

    /**
     * Refresh 토큰 삭제
     */
    public void deleteRefreshToken(Long userId) {
        if (tokenRepository.existsById(userId)) {
            tokenRepository.deleteById(userId);
        } else {
            throw new JwtException(JwtError.INVALID_REFRESH_TOKEN);
        }
    }

    /**
     * Access 토큰에 담겨있는 userId 획득
     */
    public Long getUserFromJwt(String token) {
        Claims claims = getClaim(token);
        return Long.parseLong(claims.get("userId").toString());
    }

    /**
     * token에서 claim 부분 획득
     */
    private Claims getClaim(final String token) {
        // 만료된 토큰에 대해 parseClaimsJws를 수행하면 io.jsonwebtoken.ExpiredJwtException이 발생
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * JWT의 서명을 위한 Secret Key를 가져오는 함수
     */
    private SecretKey getSigningKey() {
        // 시크릿 키 문자열을 바이트 배열로 변환 & Base64로 인코딩
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
        // HMAC SHA 알고리즘을 사용하는 Secret Key 생성
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }
}