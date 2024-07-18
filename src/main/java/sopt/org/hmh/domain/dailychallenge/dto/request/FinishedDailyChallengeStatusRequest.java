package sopt.org.hmh.domain.dailychallenge.dto.request;

public record FinishedDailyChallengeStatusRequest(
        Integer challengePeriodIndex,
        boolean isSuccess
) {
}
