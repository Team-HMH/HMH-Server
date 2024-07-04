package sopt.org.hmh.domain.admin.exception;

import sopt.org.hmh.domain.auth.exception.AuthError;
import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class AdminException extends ExceptionBase {

    public AdminException(AuthError errorBase) {
        super(errorBase);
    }
}
