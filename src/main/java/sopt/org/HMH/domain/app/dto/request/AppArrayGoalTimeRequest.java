package sopt.org.HMH.domain.app.dto.request;

import java.util.List;

public record AppArrayGoalTimeRequest(
        List<AppGoalTimeRequest> apps
) {
}
