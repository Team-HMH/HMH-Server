package sopt.org.hmh.domain.app.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.SuccessBase;

@AllArgsConstructor
public enum AppSuccess implements SuccessBase {

    ADD_APP_SUCCESS(HttpStatus.OK, "스크린타임 설정 앱 추가를 성공하였습니다."),
    DELETE_APP_SUCCESS(HttpStatus.OK,  "스크린타임 설정 앱 삭제를 성공하였습니다.")
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
