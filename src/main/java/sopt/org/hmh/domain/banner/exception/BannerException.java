package sopt.org.hmh.domain.banner.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class BannerException extends ExceptionBase {
    public BannerException(BannerError error) { super(error); }
}