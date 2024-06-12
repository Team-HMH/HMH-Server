package sopt.org.hmh.domain.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeSignUpRequest;
import sopt.org.hmh.global.auth.social.SocialPlatform;

public record SocialSignUpRequest(
        @NotNull(message = "소셜 플랫폼은 null일 수 없습니다.")
        SocialPlatform socialPlatform,
        String name,
        @JsonProperty(value = "onboarding")
        OnboardingRequest onboardingRequest,
        @JsonProperty(value = "challenge")
        ChallengeSignUpRequest challengeSignUpRequest
) {
        public ChallengeRequest toChallengeRequest() {
                return new ChallengeRequest(challengeSignUpRequest.period(), challengeSignUpRequest.goalTime());
        }
}