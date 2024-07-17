package sopt.org.hmh.domain.challenge.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppRequest;

import java.util.List;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;

public record ChallengeSignUpRequest(
        @NotNull(message = "챌린지 기간은 null일 수 없습니다.")
        Integer period,
        @NotNull(message = "챌린지 목표시간은 null일 수 없습니다.")
        Long goalTime,
        List<@Valid ChallengeAppRequest> apps
) {
        public ChallengeSignUpRequest {
                if (goalTime > ChallengeConstants.MAXIMUM_GOAL_TIME || goalTime < ChallengeConstants.MINIMUM_GOAL_TIME) {
                        throw new ChallengeException(ChallengeError.INVALID_GOAL_TIME);
                }
        }

        public ChallengeRequest toChallengeRequest() {
                return new ChallengeRequest(this.period, this.goalTime);
        }
}