package sopt.org.hmh.domain.user.domain.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class UserException extends ExceptionBase {

    public UserException(UserError errorBase) {
        super(errorBase);
    }
}
