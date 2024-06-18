package sopt.org.hmh.domain.app.dto.request;

import jakarta.validation.Valid;
import java.util.List;

public record ChallengeAppArrayRequest(
        List<@Valid ChallengeAppRequest> apps
) {
}
