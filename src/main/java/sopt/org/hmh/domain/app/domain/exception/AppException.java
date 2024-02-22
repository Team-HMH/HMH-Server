package sopt.org.hmh.domain.app.domain.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class AppException extends ExceptionBase {

    public AppException(AppError error) {
        super(error);
    }
}