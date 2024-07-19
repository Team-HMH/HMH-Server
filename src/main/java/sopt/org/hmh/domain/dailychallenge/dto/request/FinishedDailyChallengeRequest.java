package sopt.org.hmh.domain.dailychallenge.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import sopt.org.hmh.domain.app.dto.request.HistoryAppRequest;

public record FinishedDailyChallengeRequest(
    @NotNull(message = "챌린지 기간 인덱스는 null일 수 없습니다.")
    Integer challengePeriodIndex,
    List<@Valid HistoryAppRequest> apps
) {
}
