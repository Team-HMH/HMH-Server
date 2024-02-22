package sopt.org.hmh.domain.app.dto.request;

public record AppGoalTimeRequest(
        String appCode,
        Long goalTime
) {
}
