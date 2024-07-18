package sopt.org.hmh.domain.challenge.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import sopt.org.hmh.domain.app.dto.response.ChallengeAppResponse;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.util.List;

@Builder
public record ChallengeResponse(
        Integer period,
        List<Status> statuses,
        Integer todayIndex,
        String startDate,
        Long goalTime,
        List<ChallengeAppResponse> apps
) {
    public static ChallengeResponse of(Challenge challenge) {
        return ChallengeResponse.builder()
                .period(challenge.getPeriod())
                .statuses(challenge.getHistoryDailyChallenges()
                        .stream()
                        .map(DailyChallenge::getStatus)
                        .toList())
                .todayIndex(calculateTodayIndex(challenge.getCreatedAt(), challenge.getPeriod()))
                .startDate(challenge.getCreatedAt().toLocalDate().toString())
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(app -> new ChallengeAppResponse(app.getAppCode(), app.getGoalTime())).toList())
                .build();
    }

    private static Integer calculateTodayIndex(LocalDateTime challengeCreateAt, int period) {
        int daysBetween = (int) ChronoUnit.DAYS.between(challengeCreateAt.toLocalDate(), LocalDate.now());
        return (daysBetween >= period) ? -1 : daysBetween;
    }
}