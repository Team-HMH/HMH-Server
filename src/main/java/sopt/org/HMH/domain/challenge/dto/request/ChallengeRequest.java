package sopt.org.HMH.domain.challenge.dto.request;

import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;

import java.util.List;

public record ChallengeRequest(
        Integer period,
        Long goalTime,
        List<AppGoalTimeRequest> apps
) {
}
