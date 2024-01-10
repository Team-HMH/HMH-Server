package sopt.org.HMH.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.global.auth.social.SocialPlatform;

public record SocialSignUpRequest(
        SocialPlatform socialPlatform,
        String name,
        @JsonProperty(value = "onboarding")
        OnboardingRequest onboardingRequest,
        @JsonProperty(value = "challenge")
        ChallengeRequest challengeRequest
) {
}