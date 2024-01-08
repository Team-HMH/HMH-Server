package sopt.org.HMH.domain.user.domain.exception;

import sopt.org.HMH.global.common.exception.base.ExceptionBase;

public class UserException extends ExceptionBase {

    public UserException(UserError errorBase) {
        super(errorBase);
    }
}
