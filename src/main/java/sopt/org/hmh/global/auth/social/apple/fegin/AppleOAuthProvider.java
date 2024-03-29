package sopt.org.hmh.global.auth.social.apple.fegin;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.PublicKey;
import java.util.Map;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;
import sopt.org.hmh.global.auth.social.apple.response.ApplePublicKeysResponse;

@RequiredArgsConstructor
@Component
public class AppleOAuthProvider {
    private final AppleFeignClient appleFeignClient;
    private final AppleIdentityTokenParser appleIdentityTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;
    private final AppleIdentityTokenValidator appleIdentityTokenValidator;

    public String getApplePlatformId(String identityToken) {
        Map<String, String> headers = appleIdentityTokenParser.parseHeaders(identityToken);
        ApplePublicKeysResponse applePublicKeysResponse = appleFeignClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator.generatePublicKeyWithApplePublicKeys(headers, applePublicKeysResponse);
        Claims claims = appleIdentityTokenParser.parseWithPublicKeyAndGetClaims(identityToken, publicKey);
        validateClaims(claims);
        return claims.getSubject();
    }

    private void validateClaims(Claims claims) {
        if (!appleIdentityTokenValidator.isValidAppleIdentityToken(claims)) {
            throw new JwtException(JwtError.INVALID_IDENTITY_TOKEN_CLAIMS);
        }
    }
}