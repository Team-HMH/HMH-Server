package sopt.org.HMH.domain.dailychallenge.dto.response;

import lombok.Builder;
import sopt.org.HMH.domain.app.dto.response.AppGoalTimeResponse;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;

import java.util.List;

@Builder
public record DailyChallengeResponse(
        String status,
        Long goalTime,
        List<AppGoalTimeResponse> apps
) {
    public static DailyChallengeResponse of(DailyChallenge dailyChallenge, String os) {
        return DailyChallengeResponse.builder()
                .status(dailyChallenge.getStatus().toString())
                .goalTime(dailyChallenge.getGoalTime())
                .apps(dailyChallenge.getApps()
                        .stream()
                        .filter(app -> os.equals(app.getOs()))
                        .map(app -> new AppGoalTimeResponse(app.getAppCode(), app.getGoalTime()))
                        .toList())
                .build();
    }
}