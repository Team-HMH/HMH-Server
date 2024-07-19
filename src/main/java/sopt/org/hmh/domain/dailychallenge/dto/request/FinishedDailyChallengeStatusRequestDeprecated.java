package sopt.org.hmh.domain.dailychallenge.dto.request;

import java.time.LocalDate;

@Deprecated
public record FinishedDailyChallengeStatusRequestDeprecated(
        LocalDate challengeDate,
        boolean isSuccess
) {

}
