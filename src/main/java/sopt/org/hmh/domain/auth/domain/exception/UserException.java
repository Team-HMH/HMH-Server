package sopt.org.hmh.domain.auth.domain.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class UserException extends ExceptionBase {

    public UserException(UserError errorBase) {
        super(errorBase);
    }
}