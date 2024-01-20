package sopt.org.HMH.domain.app.domain.exception;

import sopt.org.HMH.global.common.exception.base.ExceptionBase;

public class AppException extends ExceptionBase {

    public AppException(AppError error) {
        super(error);
    }
}