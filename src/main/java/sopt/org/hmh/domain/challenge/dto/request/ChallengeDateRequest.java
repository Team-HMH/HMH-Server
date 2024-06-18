package sopt.org.hmh.domain.challenge.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ChallengeDateRequest(
        @NotNull(message = "챌린지 날짜는 null일 수 없습니다.")
        LocalDate challengeDate
) {
}