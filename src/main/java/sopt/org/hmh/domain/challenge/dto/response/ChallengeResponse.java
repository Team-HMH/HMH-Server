package sopt.org.hmh.domain.challenge.dto.response;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import sopt.org.hmh.domain.app.dto.response.ChallengeAppResponse;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.util.Comparator;
import java.util.List;

@Builder
public record ChallengeResponse(
        Integer period,
        List<Status> statuses,
        Integer todayIndex,
        LocalDate startDate,
        Long goalTime,
        List<ChallengeAppResponse> apps
) {
    public static ChallengeResponse of(Challenge challenge, String timeZone) {
        return ChallengeResponse.builder()
                .period(challenge.getPeriod())
                .statuses(challenge.getHistoryDailyChallenges()
                        .stream()
                        .sorted(Comparator.comparing(DailyChallenge::getChallengeDate))
                        .map(DailyChallenge::getStatus)
                        .toList())
                .todayIndex(calculateTodayIndex(challenge, LocalDate.now(ZoneId.of(timeZone))))
                .startDate(challenge.getStartDate())
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(app -> new ChallengeAppResponse(app.getAppCode(), app.getGoalTime())).toList())
                .build();
    }

    private static Integer calculateTodayIndex(Challenge challenge, LocalDate now) {
        final int COMPLETED_CHALLENGE_INDEX = -1;
        int daysBetween = (int) ChronoUnit.DAYS.between(challenge.getStartDate(), now);
        return (daysBetween >= challenge.getPeriod()) ? COMPLETED_CHALLENGE_INDEX : daysBetween;
    }
}