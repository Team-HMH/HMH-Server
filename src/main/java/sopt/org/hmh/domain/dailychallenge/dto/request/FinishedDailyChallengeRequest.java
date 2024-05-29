package sopt.org.hmh.domain.dailychallenge.dto.request;

import java.time.LocalDate;
import java.util.List;
import sopt.org.hmh.domain.app.dto.request.HistoryAppRequest;

public record FinishedDailyChallengeRequest(
        LocalDate challengeDate,
        List<HistoryAppRequest> apps
) {
}
