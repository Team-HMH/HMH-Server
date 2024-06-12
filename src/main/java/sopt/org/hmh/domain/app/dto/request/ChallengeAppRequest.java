package sopt.org.hmh.domain.app.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChallengeAppRequest(
        @NotNull(message = "앱 코드는 null일 수 없습니다.")
        String appCode,
        Long goalTime
) {
}
