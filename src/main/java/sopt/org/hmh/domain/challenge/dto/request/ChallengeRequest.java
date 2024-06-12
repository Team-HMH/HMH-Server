package sopt.org.hmh.domain.challenge.dto.request;

import jakarta.validation.constraints.NotNull;
import sopt.org.hmh.domain.app.domain.AppConstants;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;

public record ChallengeRequest(
        @NotNull(message = "챌린지 기간은 null일 수 없습니다.")
        Integer period,
        @NotNull(message = "챌린지 목표시간은 null일 수 없습니다.")
        Long goalTime
) {

    public ChallengeRequest {
        if (!ChallengeConstants.AVAILABLE_CHALLENGE_PERIODS.contains(period)) {
            throw new ChallengeException(ChallengeError.INVALID_PERIOD_NUMERIC);
        }
        if (goalTime > AppConstants.MAXIMUM_APP_TIME || goalTime < AppConstants.MINIMUM_APP_TIME) {
            throw new AppException(AppError.INVALID_TIME_RANGE);
        }
    }
}
