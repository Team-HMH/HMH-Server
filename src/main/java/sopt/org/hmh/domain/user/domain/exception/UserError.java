package sopt.org.hmh.domain.user.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.ErrorBase;

@AllArgsConstructor
public enum UserError implements ErrorBase {

    // 404 NOT FOUND
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    NOT_ENOUGH_POINTS(HttpStatus.BAD_REQUEST, "유저의 포인트가 부족합니다."),
    NOT_FOUND_CURRENT_CHALLENGE_ID(HttpStatus.NOT_FOUND, "유저에서 현재 챌린지 id 정보를 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String errorMessage;

    @Override
    public int getHttpStatusCode() {
        return this.status.value();
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