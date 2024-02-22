package sopt.org.hmh.domain.app.dto.request;

import java.util.List;

public record AppArrayGoalTimeRequest(
        List<AppGoalTimeRequest> apps
) {
}
