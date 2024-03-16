package sopt.org.hmh.domain.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeSignUpRequest;
import sopt.org.hmh.global.auth.social.SocialPlatform;

public record SocialSignUpRequest(
        SocialPlatform socialPlatform,
        String name,
        @JsonProperty(value = "onboarding")
        OnboardingRequest onboardingRequest,
        @JsonProperty(value = "challenge")
        ChallengeSignUpRequest challengeSignUpRequest
) {
}