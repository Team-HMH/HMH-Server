package sopt.org.HMH.domain.challenge.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.HMH.global.common.exception.base.SuccessBase;

@AllArgsConstructor
public enum ChallengeSuccess implements SuccessBase {

    ADD_CHALLENGE_SUCCESS(HttpStatus.OK, "챌린지 생성 성공"),
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