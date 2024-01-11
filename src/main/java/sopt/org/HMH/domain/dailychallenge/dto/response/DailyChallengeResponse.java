package sopt.org.HMH.domain.dailychallenge.dto.response;

import sopt.org.HMH.domain.app.dto.request.AppArrayGoalTimeRequest;

public record DailyChallengeResponse(
        String status,
        Long goalTime,
        AppArrayGoalTimeRequest apps
) {
}