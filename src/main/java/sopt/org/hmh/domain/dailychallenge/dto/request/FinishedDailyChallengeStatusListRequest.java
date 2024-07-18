package sopt.org.hmh.domain.dailychallenge.dto.request;

import java.util.List;

public record FinishedDailyChallengeStatusListRequest(
        List<FinishedDailyChallengeStatusRequest> finishedDailyChallenges
) {
}
