package sopt.org.HMH.domain.challenge.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.val;
import sopt.org.HMH.domain.app.dto.response.AppGoalTimeResponse;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.domain.Status;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ChallengeResponse(
        Integer period,
        List<Status> statuses,
        Integer todayIndex,
        Long goalTime,
        List<AppGoalTimeResponse> apps
) {
    public static ChallengeResponse of(Challenge challenge, String os) {
        List<DailyChallenge> dailyChallenges = challenge.getDailyChallenges();
        DailyChallenge startDayOfChallenge = challenge.getDailyChallenges().get(0);

        int daysSinceToday = calculateDaysSinceToday(startDayOfChallenge.getCreatedAt());
        int todayIndex = daysSinceToday >= challenge.getPeriod() ? -1 : daysSinceToday;
        int dailyChallengeIndex = todayIndex == -1 ? dailyChallenges.size()-1 : todayIndex;

        DailyChallenge todayDailyChallenge = dailyChallenges.get(dailyChallengeIndex);

        List<Status> statuses = new ArrayList<>();
        for (val dailyChallenge : dailyChallenges) {
            statuses.add(dailyChallenge.getStatus());
        }

        return ChallengeResponse.builder()
                .period(challenge.getPeriod())
                .statuses(statuses)
                .todayIndex(todayIndex)
                .goalTime(todayDailyChallenge.getGoalTime())
                .apps(todayDailyChallenge.getApps()
                        .stream()
                        .filter(app -> os.equals(app.getOs()))
                        .map(AppGoalTimeResponse::of)
                        .toList())
                .build();
    }

    private static Integer calculateDaysSinceToday(LocalDateTime dateToCompare) {
        return (int) ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(), dateToCompare.toLocalDate());
    }
}