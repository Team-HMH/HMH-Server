package sopt.org.hmh.domain.challenge.dto.response;

import lombok.Builder;
import sopt.org.hmh.domain.app.dto.response.ChallengeAppResponse;
import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.util.List;

@Builder
public record DailyChallengeResponse(
        Status status,
        Long goalTime,
        List<ChallengeAppResponse> apps
) {
}