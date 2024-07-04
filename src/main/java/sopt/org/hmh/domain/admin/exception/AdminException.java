package sopt.org.hmh.domain.admin.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class AdminException extends ExceptionBase {

    public AdminException(AdminError errorBase) {
        super(errorBase);
    }
}
