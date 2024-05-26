package sopt.org.hmh.domain.app.dto.request;

public record ChallengeAppRequest(
        String appCode,
        Long goalTime
) {
}
