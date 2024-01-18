package sopt.org.HMH.domain.challenge.dto.response;

import lombok.Builder;
import sopt.org.HMH.domain.app.dto.response.AppGoalTimeResponse;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.domain.Status;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Builder
public record ChallengeResponse(
        Integer period,
        List<Status> statuses,
        Integer todayIndex,
        Long goalTime,
        List<AppGoalTimeResponse> apps
) {
    public static ChallengeResponse of(Challenge challenge, String os) {
        List<DailyChallenge> dailyChallenges = challenge.getDailyChallenges();

        int daysSinceToday = (int) ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(),
                challenge.getDailyChallenges().get(0).getCreatedAt().toLocalDate());
        int todayIndex = daysSinceToday >= challenge.getPeriod() ? -1 : daysSinceToday;
        int dailyChallengeIndex = todayIndex == -1 ? dailyChallenges.size()-1 : todayIndex;

        return ChallengeResponse.builder()
                .period(challenge.getPeriod())
                .statuses(dailyChallenges.stream()
                        .map(dailyChallenge -> { return dailyChallenge.getStatus(); })
                        .toList())
                .todayIndex(todayIndex)
                .goalTime(dailyChallenges.get(dailyChallengeIndex).getGoalTime())
                .apps(dailyChallenges.get(dailyChallengeIndex).getApps()
                        .stream()
                        .filter(app -> os.equals(app.getOs()))
                        .map(app -> new AppGoalTimeResponse(app.getAppCode(), app.getGoalTime()))
                        .toList())
                .build();
    }
}