package sopt.org.hmh.domain.challenge.dto.response;

import lombok.Builder;
import sopt.org.hmh.domain.app.dto.response.AppGoalTimeResponse;
import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.util.List;

@Builder
public record DailyChallengeResponse(
        Status status,
        Long goalTime,
        List<AppGoalTimeResponse> apps
) {
}