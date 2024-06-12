package sopt.org.hmh.domain.app.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import sopt.org.hmh.domain.app.domain.AppConstants;

public record ChallengeAppRequest(
        @NotNull(message = "앱 코드는 null일 수 없습니다.")
        String appCode,
        @NotNull(message = "앱 시간은 null일 수 없습니다.")
        @Max(value = AppConstants.MAXIMUM_APP_TIME, message = "앱 시간은 최대 앱 시간 이상으로 설정할 수 없습니다.")
        @Min(value = AppConstants.MINIMUM_APP_TIME, message = "앱 시간은 최소 앱 시간 이하로 설정할 수 없습니다.")
        Long goalTime
) {

}
