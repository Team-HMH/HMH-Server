package sopt.org.HMH.global.common.exception.base;

/**
 * 하위 도메인 예외는 본 Exception 클래스를 상속 받습니다.
 */
public abstract class ExceptionBase extends RuntimeException {
    private static final String ERROR_MESSAGE_HEADER = "ERROR : ";

    private final ErrorBase errorBase;

    protected ExceptionBase(ErrorBase errorBase) {
        super(ERROR_MESSAGE_HEADER + errorBase.getErrorMessage());
        this.errorBase = errorBase;
    }

    public ErrorBase getError() {
        return errorBase;
    }
}