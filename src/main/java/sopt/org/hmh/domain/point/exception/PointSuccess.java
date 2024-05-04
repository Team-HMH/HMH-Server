package sopt.org.hmh.domain.point.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.SuccessBase;

@AllArgsConstructor
public enum PointSuccess implements SuccessBase {

    POINT_USAGE_SUCCESS(HttpStatus.OK, "포인트 사용에 성공하였습니다"),
    POINT_EARN_SUCCESS(HttpStatus.OK, "포인트 받기에 성공하였습니다"),
    GET_USAGE_POINT_SUCCESS(HttpStatus.OK, "사용할 포인트 반환에 성공하였습니다"),
    ;

    private final HttpStatus status;
    private final String successMessage;

    @Override
    public int getHttpStatusCode() {
        return this.status.value();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }

    @Override
    public String getSuccessMessage() {
        return this.successMessage;
    }

}
