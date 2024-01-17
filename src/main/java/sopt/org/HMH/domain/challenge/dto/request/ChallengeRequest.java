package sopt.org.HMH.domain.challenge.dto.request;

public record ChallengeRequest(
        Integer period,
        Long goalTime
) {
}