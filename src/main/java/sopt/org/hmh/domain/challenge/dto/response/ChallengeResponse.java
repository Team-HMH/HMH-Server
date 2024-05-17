package sopt.org.hmh.domain.challenge.dto.response;

import lombok.Builder;
import sopt.org.hmh.domain.app.dto.response.AppGoalTimeResponse;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Builder
public record ChallengeResponse(
        Integer period,
        List<Status> statuses,
        Integer todayIndex,
        String startDate,
        Long goalTime,
        List<AppGoalTimeResponse> apps
) {
}