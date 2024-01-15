package sopt.org.HMH.global.auth;

import static java.util.Objects.isNull;

import java.security.Principal;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;

public class UserId {
    public static Long getUserId(Principal principal) {
        if (isNull(principal)) {
            throw new JwtException(JwtError.EMPTY_PRINCIPLE_EXCEPTION);
        }
        return Long.valueOf(principal.getName());
    }
}
