package sopt.org.hmh.global.auth.social.apple.fegin;

import org.springframework.stereotype.Component;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import java.math.BigInteger;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;
import sopt.org.hmh.global.auth.social.apple.response.ApplePublicKeyResponse;
import sopt.org.hmh.global.auth.social.apple.response.ApplePublicKeysResponse;

@Component
public class ApplePublicKeyGenerator {
    public PublicKey generatePublicKeyWithApplePublicKeys(Map<String, String> headers, ApplePublicKeysResponse applePublicKeysResponse) {
        ApplePublicKeyResponse applePublicKeyResponse = applePublicKeysResponse
                .getMatchesKey(headers.get("alg"), headers.get("kid"));

        byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKeyResponse.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKeyResponse.e());

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(
                new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKeyResponse.kty());
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new JwtException(JwtError.UNABLE_TO_CREATE_APPLE_PUBLIC_KEY);
        }
    }
}