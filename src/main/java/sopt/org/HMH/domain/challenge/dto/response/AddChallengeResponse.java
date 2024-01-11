package sopt.org.HMH.domain.challenge.dto.response;

public record AddChallengeResponse(
        Long challengeId
) {
    public static AddChallengeResponse of(Long challengeId) {
        return new AddChallengeResponse(challengeId);
    }
}