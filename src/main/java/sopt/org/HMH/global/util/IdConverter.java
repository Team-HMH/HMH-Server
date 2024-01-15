package sopt.org.HMH.global.util;

import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;

import java.security.Principal;

import static java.util.Objects.isNull;

public class IdConverter {

    public static Long getUserId(Principal principal) {
        if (isNull(principal)) {
            throw new JwtException(JwtError.EMPTY_PRINCIPLE_EXCEPTION);
        }
        return Long.valueOf(principal.getName());
    }
}