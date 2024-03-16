package sopt.org.hmh.domain.auth.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class AuthException extends ExceptionBase {

    public AuthException(AuthError errorBase) {
        super(errorBase);
    }
}