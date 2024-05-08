package sopt.org.hmh.domain.dailychallenge.domain.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.org.hmh.global.common.exception.base.SuccessBase;

@AllArgsConstructor
public enum DailyChallengeSuccess implements SuccessBase {

    SEND_FINISHED_DAILY_CHALLENGE_SUCCESS(HttpStatus.OK, "완료된 일별 챌린지 정보 전송을 성공하였습니다"),
    MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS(HttpStatus.OK, "오늘의 일별 챌린지 STATUS 실패 처리를 성공하였습니다"),
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