package sopt.org.HMH.global.auth.social.apple.request;

import java.util.List;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;

public class ApplePublicKeys {
    private List<ApplePublicKey> keys;

    public ApplePublicKey getMatchesKey(String alg, String kid) {
        return keys.stream()
                .filter(applePublicKey -> applePublicKey.alg().equals(alg) && applePublicKey.kid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new JwtException(JwtError.INVALID_IDENTITY_TOKEN));
    }
}