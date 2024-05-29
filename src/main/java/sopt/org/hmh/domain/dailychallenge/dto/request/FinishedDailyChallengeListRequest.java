package sopt.org.hmh.domain.dailychallenge.dto.request;

import java.util.List;

public record FinishedDailyChallengeListRequest(
        List<FinishedDailyChallengeRequest> finishedDailyChallenges
) {

}
