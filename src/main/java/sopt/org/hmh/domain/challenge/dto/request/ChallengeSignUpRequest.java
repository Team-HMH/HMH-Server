package sopt.org.hmh.domain.challenge.dto.request;

import sopt.org.hmh.domain.app.dto.request.AppGoalTimeRequest;

import java.util.List;

public record ChallengeSignUpRequest(
        Integer period,
        Long goalTime,
        List<AppGoalTimeRequest> apps
) {
}