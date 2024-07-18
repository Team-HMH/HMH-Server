package sopt.org.hmh.domain.dailychallenge.dto.request;

import jakarta.validation.Valid;
import java.util.List;

public record FinishedDailyChallengeStatusListRequest(
        List<@Valid FinishedDailyChallengeStatusRequest> finishedDailyChallenges
) {
}
