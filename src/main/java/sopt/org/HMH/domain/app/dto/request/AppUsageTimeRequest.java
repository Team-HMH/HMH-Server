package sopt.org.HMH.domain.app.dto.request;

public record AppUsageTimeRequest(
        String appCode,
        Long usageTime
) {
}