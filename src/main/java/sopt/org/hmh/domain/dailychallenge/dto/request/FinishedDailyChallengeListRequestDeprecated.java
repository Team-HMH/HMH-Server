package sopt.org.hmh.domain.dailychallenge.dto.request;

import jakarta.validation.Valid;
import java.util.List;

@Deprecated
public record FinishedDailyChallengeListRequestDeprecated(
        List<@Valid FinishedDailyChallengeRequestDeprecated> finishedDailyChallenges
) {

}
