package sopt.org.HMH.domain.dailychallenge.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record DailyChallengeResponse(
        String status,
        Long goalTime,
        List<AppGoalTimeResponse> apps
) {
    public static DailyChallengeResponse of(DailyChallenge dailyChallenge) {
        return DailyChallengeResponse.builder()
                .status(dailyChallenge.getStatus().toString())
                .goalTime(dailyChallenge.getGoalTime())
                .apps(dailyChallenge.getApps().stream().map(AppGoalTimeResponse::of).toList())
                .build();
    }

    public record AppGoalTimeResponse(
            String appCode,
            Long goalTime
    ) {
        static AppGoalTimeResponse of(App app) {
            return new AppGoalTimeResponse(app.getAppCode(), app.getGoalTime());
        }
    }
}