package sopt.org.HMH.domain.app.dto.response;

import sopt.org.HMH.domain.app.domain.App;

public record AppGoalTimeResponse(
        String appCode,
        Long goalTime
) {
    static AppGoalTimeResponse of(App app) {
        return new AppGoalTimeResponse(app.getAppCode(), app.getGoalTime());
    }
}