package sopt.org.HMH.domain.app.dto.request;

import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.dto.response.AppGoalTimeResponse;

public record AppGoalTimeRequest(
        String appCode,
        Long goalTime
) {
    public static AppGoalTimeRequest of(App app) {
        return new AppGoalTimeRequest(app.getAppCode(), app.getGoalTime());
    }
}