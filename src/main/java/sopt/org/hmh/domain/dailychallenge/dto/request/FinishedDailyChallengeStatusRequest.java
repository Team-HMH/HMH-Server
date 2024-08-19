package sopt.org.hmh.domain.dailychallenge.dto.request;

import jakarta.validation.constraints.NotNull;

public record FinishedDailyChallengeStatusRequest(
        @NotNull(message = "챌린지 기간 인덱스는 null일 수 없습니다.")
        Integer challengePeriodIndex,
        boolean isSuccess
) {
}
