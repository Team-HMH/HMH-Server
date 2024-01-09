package sopt.org.HMH.domain.app.dto.request;

public record AppGoalTimeRequest(
        String appCode,
        Long goalTime
) {
}