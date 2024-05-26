package sopt.org.hmh.domain.point.dto.response;

import java.util.List;

public record ChallengePointStatusListResponse(
        Integer point,
        List<ChallengePointStatusResponse> challengePointStatuses
) {
}
