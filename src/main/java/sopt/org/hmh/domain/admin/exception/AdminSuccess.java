package sopt.org.hmh.domain.admin.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.SuccessBase;

@AllArgsConstructor
public enum AdminSuccess implements SuccessBase {

    // 200 OK
    ADMIN_LOGIN_SUCCESS(HttpStatus.OK, "관리자 로그인에 성공했습니다."),

    // 204 NO CONTENT
    ADMIN_WITHDRAW_IMMEDIATELY_SUCCESS(HttpStatus.NO_CONTENT, "관리자 권한으로 유저 즉시 삭제에 성공했습니다."),
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
