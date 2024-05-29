package sopt.org.hmh.global.auth.social.apple.fegin;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleIdentityTokenValidator {

    @Value("${oauth2.apple.iss}")
    private String iss;

    @Value("${oauth2.apple.client-id}")
    private String clientId;
    public boolean isValidAppleIdentityToken(Claims claims) {
        return claims.getIssuer().contains(iss)
                && claims.getAudience().equals(clientId);
    }
}