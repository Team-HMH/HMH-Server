package sopt.org.hmh.domain.app.dto.request;

import java.util.List;

public record AppArrayUsageTimeRequest(
        List<AppUsageTimeRequest> apps
) {
}
