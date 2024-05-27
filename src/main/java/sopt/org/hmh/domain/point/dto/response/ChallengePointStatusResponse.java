package sopt.org.hmh.domain.point.dto.response;

import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.time.LocalDate;

public record ChallengePointStatusResponse(
        LocalDate challengeDate,
        Status status
) {
}
