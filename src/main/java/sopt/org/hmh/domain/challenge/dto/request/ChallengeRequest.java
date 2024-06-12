package sopt.org.hmh.domain.challenge.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;

public record ChallengeRequest(
        @NotNull(message = "챌린지 기간은 null일 수 없습니다.")
        Integer period,
        @Max(value = ChallengeConstants.MAXIMUM_GOAL_TIME, message = "챌린지 목표 시간은 최대 목표 시간 이상으로 설정할 수 없습니다.")
        @Min(value = ChallengeConstants.MINIMUM_GOAL_TIME, message = "챌린지 목표 시간은 최소 목표 시간 이하로 설정할 수 없습니다.")
        @NotNull(message = "챌린지 목표시간은 null일 수 없습니다.")
        Long goalTime
) {

    public ChallengeRequest {
        if (!ChallengeConstants.AVAILABLE_CHALLENGE_PERIODS.contains(period)) {
            throw new ChallengeException(ChallengeError.INVALID_PERIOD_NUMERIC);
        }
    }
}
