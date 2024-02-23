package sopt.org.hmh.global.auth.social.apple.fegin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;

@Component
public class AppleIdentityTokenParser {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Map<String, String> parseHeaders(String identityToken) {
        try {
            String encoded = identityToken.split("\\.")[0];
            String decoded = new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8);
            return OBJECT_MAPPER.readValue(decoded, Map.class);
        } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
            throw new JwtException(JwtError.INVALID_IDENTITY_TOKEN.getErrorMessage());
        }
    }

    public Claims parseWithPublicKeyAndGetClaims(String identityToken, PublicKey publicKey) {
        try {
            return getJwtParser(publicKey)
                    .parseClaimsJws(identityToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException(JwtError.EXPIRED_IDENTITY_TOKEN.getErrorMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new JwtException(JwtError.INVALID_IDENTITY_TOKEN.getErrorMessage());
        }
    }

    private JwtParser getJwtParser(Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }
}