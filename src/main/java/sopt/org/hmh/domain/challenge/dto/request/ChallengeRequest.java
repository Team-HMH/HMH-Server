package sopt.org.hmh.domain.challenge.dto.request;

public record ChallengeRequest(
        Integer period,
        Long goalTime
) {
}