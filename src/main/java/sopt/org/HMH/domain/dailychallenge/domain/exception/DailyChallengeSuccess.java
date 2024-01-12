package sopt.org.HMH.domain.dailychallenge.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.HMH.global.common.exception.base.SuccessBase;

@AllArgsConstructor
public enum DailyChallengeSuccess implements SuccessBase {

    GET_DAY_CHALLENGE_SUCCESS(HttpStatus.OK, "오늘의 일별 챌린지 이용 통계 조회 성공"),
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