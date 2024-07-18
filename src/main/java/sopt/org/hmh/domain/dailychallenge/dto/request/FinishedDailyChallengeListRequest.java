package sopt.org.hmh.domain.dailychallenge.dto.request;

import jakarta.validation.Valid;
import java.util.List;

public record FinishedDailyChallengeListRequest(
        List<@Valid FinishedDailyChallengeRequest> finishedDailyChallenges
) {

}
