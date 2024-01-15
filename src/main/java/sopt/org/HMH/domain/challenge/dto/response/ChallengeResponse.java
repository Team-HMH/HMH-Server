package sopt.org.HMH.domain.challenge.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.val;
import sopt.org.HMH.domain.app.dto.response.AppGoalTimeResponse;
import sopt.org.HMH.domain.challenge.domain.Challenge;
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
        val dailyChallenges = challenge.getDailyChallenges();

        val statuses = new ArrayList<Status>();
        for (val dailyChallenge : dailyChallenges) {
            statuses.add(dailyChallenge.getStatus());
        }

        val startDayOfChallenge = challenge.getDailyChallenges().get(0);
        val todayIndex = calculateDaysSinceToday(startDayOfChallenge.getCreatedAt());
        val todayDailyChallenge = dailyChallenges.get(todayIndex);

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