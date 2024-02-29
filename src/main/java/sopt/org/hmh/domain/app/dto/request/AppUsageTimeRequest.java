package sopt.org.hmh.domain.app.dto.request;

public record AppUsageTimeRequest(
        String appCode,
        Long usageTime
) {
}