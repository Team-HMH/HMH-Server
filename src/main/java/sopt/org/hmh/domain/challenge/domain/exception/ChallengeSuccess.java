package sopt.org.hmh.domain.challenge.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.SuccessBase;

@AllArgsConstructor
public enum ChallengeSuccess implements SuccessBase {

    GET_CHALLENGE_SUCCESS(HttpStatus.OK, "챌린지 달성현황 조회 성공하였습니다."),
    ADD_CHALLENGE_SUCCESS(HttpStatus.OK, "챌린지 생성을 성공하였습니다."),
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