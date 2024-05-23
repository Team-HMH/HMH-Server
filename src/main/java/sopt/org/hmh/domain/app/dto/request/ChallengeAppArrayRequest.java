package sopt.org.hmh.domain.app.dto.request;

import java.util.List;

public record ChallengeAppArrayRequest(
        List<ChallengeAppRequest> apps
) {
}
