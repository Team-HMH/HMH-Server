package sopt.org.HMH.domain.challenge.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import sopt.org.HMH.domain.app.dto.response.AppGoalTimeResponse;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.domain.Status;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ChallengeResponse(
        Integer period,
        List<Status> statuses,
        Integer todayIndex,
        Long goalTime,
        List<AppGoalTimeResponse> apps
) {
    public static ChallengeResponse of(Challenge challenge, DailyChallenge dailyChallenge, List<Status> statuses, Integer todayIndex) {
        return ChallengeResponse.builder()
                .period(challenge.getPeriod())
                .statuses(statuses)
                .todayIndex(todayIndex)
                .goalTime(dailyChallenge.getGoalTime())
                .apps(dailyChallenge.getApps().stream().map(AppGoalTimeResponse::of).toList())
                .build();
    }
}