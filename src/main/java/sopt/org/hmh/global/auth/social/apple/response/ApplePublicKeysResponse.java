package sopt.org.hmh.global.auth.social.apple.response;

import java.util.List;
import lombok.Getter;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;

@Getter
public class ApplePublicKeysResponse {
    private List<ApplePublicKeyResponse> keys;

    public ApplePublicKeyResponse getMatchesKey(String alg, String kid) {
        return keys.stream()
                .filter(applePublicKeyResponse -> applePublicKeyResponse.alg().equals(alg) && applePublicKeyResponse.kid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new JwtException(JwtError.INVALID_IDENTITY_TOKEN));
    }
}