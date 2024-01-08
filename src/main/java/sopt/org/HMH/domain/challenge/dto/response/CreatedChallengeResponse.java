package sopt.org.HMH.domain.challenge.dto.response;

public record CreatedChallengeResponse(
        Long challengeId
) {
    public static CreatedChallengeResponse of(Long challengeId) {
        return new CreatedChallengeResponse(challengeId);
    }
}