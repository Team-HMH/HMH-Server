package sopt.org.hmh.domain.app.dto.request;

import jakarta.validation.constraints.NotNull;

public record HistoryAppRequest(
        @NotNull(message = "앱 코드는 null일 수 없습니다.")
        String appCode,
        @NotNull(message = "앱 사용시간은 null일 수 없습니다.")
        Long usageTime
) {
}