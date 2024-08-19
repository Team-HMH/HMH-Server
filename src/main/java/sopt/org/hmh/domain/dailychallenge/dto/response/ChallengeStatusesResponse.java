package sopt.org.hmh.domain.dailychallenge.dto.response;

import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.util.List;

public record ChallengeStatusesResponse(
        List<Status> statuses
) {
}
