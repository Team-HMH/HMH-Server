package sopt.org.hmh.domain.dailychallenge.dto.request;

import java.time.LocalDate;

public record FinishedDailyChallengeStatusRequest(
        LocalDate challengeDate,
        boolean isSuccess
) {

}
