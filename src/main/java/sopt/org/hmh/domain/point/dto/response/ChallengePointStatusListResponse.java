package sopt.org.hmh.domain.point.dto.response;

import java.util.List;

public record ChallengePointStatusListResponse(
        List<ChallengePointStatusResponse> challengePointStatuses
) {
}
