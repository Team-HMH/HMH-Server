package sopt.org.hmh.global.auth.jwt.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class JwtException extends ExceptionBase {

    public JwtException(JwtError errorBase) {
        super(errorBase);
    }
}