package sopt.org.HMH.global.common.exception;

import sopt.org.HMH.global.common.exception.base.ExceptionBase;

/**
 * 전역적 혹은 시스템 전체 범위에서 발생하는 예외
 */
public class GlobalException extends ExceptionBase {

    public GlobalException(GlobalError error) {
        super(error);
    }
}