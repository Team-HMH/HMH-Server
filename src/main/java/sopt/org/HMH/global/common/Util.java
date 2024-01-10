package sopt.org.HMH.global.common;

import java.security.Principal;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;

import static java.util.Objects.isNull;

public class Util {

    /**
     * Principal 객체로부터 User의 식별자를 추출하는 메서드
     */
    public static Long getUserId(Principal principal) {
        if (isNull(principal)) {
            throw new JwtException(JwtError.EMPTY_PRINCIPLE_EXCEPTION);
        }
        return Long.valueOf(principal.getName());
    }
}
