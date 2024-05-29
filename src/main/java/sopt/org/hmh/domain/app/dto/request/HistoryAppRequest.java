package sopt.org.hmh.domain.app.dto.request;

public record HistoryAppRequest(
        String appCode,
        Long usageTime
) {
}