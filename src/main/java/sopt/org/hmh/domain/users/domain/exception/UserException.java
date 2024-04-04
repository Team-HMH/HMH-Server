package sopt.org.hmh.domain.users.domain.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class UserException extends ExceptionBase {

    public UserException(UserError errorBase) {
        super(errorBase);
    }
}
