package sopt.org.hmh.global.auth.jwt.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtPrefixExtractor {

    public static String extractPrefix(String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(JwtConstants.BEARER)) {
            return accessToken.substring(JwtConstants.BEARER.length());
        }
        throw new JwtException(JwtError.INVALID_ACCESS_TOKEN);
    }

}
