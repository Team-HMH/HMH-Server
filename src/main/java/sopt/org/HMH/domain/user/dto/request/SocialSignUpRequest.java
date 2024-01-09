package sopt.org.HMH.domain.user.dto.request;

public record SocialSignUpRequest(

        SocialPlatformRequest socialPlatformRequest,
        OnboardingRequest onboardingRequest,
        ChallengeRequest challengeRequest
) {
}