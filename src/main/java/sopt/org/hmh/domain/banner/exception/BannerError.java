package sopt.org.hmh.domain.banner.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum BannerError implements ErrorBase {

    BANNER_NOT_FOUND(HttpStatus.NOT_FOUND, "배너를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String errorMessage;

    @Override
    public int getHttpStatusCode() {
        return status.value();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}