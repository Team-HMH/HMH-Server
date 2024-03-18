package sopt.org.hmh.domain.user.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.SuccessBase;

@AllArgsConstructor
public enum UserSuccess implements SuccessBase {

    // 200 OK
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "토큰 재발급에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공했습니다."),
    GET_USER_INFO_SUCCESS(HttpStatus.OK, "유저의 정보를 불러오는데에 성공했습니다."),
    GET_USER_POINT_SUCCESS(HttpStatus.OK, "유저의 포인트 정보를 불러오는데에 성공했습니다."),
    WITHDRAW_SUCCESS(HttpStatus.OK, "회원 탈퇴를 성공하였습니다."),
    GET_SOCIAL_ACCESS_TOKEN_SUCCESS(HttpStatus.OK, "소셜 액세스 토큰 발급을 성공했습니다."),

    // 201 CREATED
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원 가입에 성공했습니다."),
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