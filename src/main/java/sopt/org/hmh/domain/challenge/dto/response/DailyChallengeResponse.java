package sopt.org.hmh.domain.challenge.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import sopt.org.hmh.domain.app.dto.response.ChallengeAppResponse;
import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.util.List;

@Builder
public record DailyChallengeResponse(
        @NotNull(message = "상태 값은 null일 수 없습니다.") Status status,
        @NotNull(message = "목표시간은 null일 수 없습니다.") Long goalTime,
        List<ChallengeAppResponse> apps
) {
}