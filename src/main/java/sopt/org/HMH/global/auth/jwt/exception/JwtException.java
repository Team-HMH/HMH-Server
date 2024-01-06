package sopt.org.HMH.global.auth.jwt.exception;

import sopt.org.HMH.global.common.exception.base.ExceptionBase;

public class JwtException extends ExceptionBase {
    public JwtException(JwtError errorBase) {
        super(errorBase);
    }
}