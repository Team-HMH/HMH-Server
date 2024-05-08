package sopt.org.hmh.domain.dailychallenge.dto.request;

import java.time.LocalDate;
import java.util.List;
import sopt.org.hmh.domain.app.dto.request.AppUsageTimeRequest;

public record FinishedDailyChallengeRequest(
        LocalDate challengeDate,
        List<AppUsageTimeRequest> apps
) {
}
