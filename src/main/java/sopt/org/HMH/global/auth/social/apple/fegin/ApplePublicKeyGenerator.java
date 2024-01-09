package sopt.org.HMH.global.auth.social.apple.fegin;

import org.springframework.stereotype.Component;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import java.math.BigInteger;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;
import sopt.org.HMH.global.auth.social.apple.request.ApplePublicKey;
import sopt.org.HMH.global.auth.social.apple.request.ApplePublicKeys;

@Component
public class ApplePublicKeyGenerator {
    public PublicKey generatePublicKeyWithApplePublicKeys(Map<String, String> headers, ApplePublicKeys applePublicKeys) {
        ApplePublicKey applePublicKey = applePublicKeys
                .getMatchesKey(headers.get("alg"), headers.get("kid"));

        byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.e());

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(
                new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.kty());
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new JwtException(JwtError.UNABLE_TO_CREATE_APPLE_PUBLIC_KEY);
        }
    }
}