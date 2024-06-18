package sopt.org.hmh.domain.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeSignUpRequest;
import sopt.org.hmh.domain.user.domain.OnboardingInfo;
import sopt.org.hmh.domain.user.domain.OnboardingProblem;
import sopt.org.hmh.global.auth.social.SocialPlatform;

public record SocialSignUpRequest(
        @NotNull(message = "소셜 플랫폼은 null일 수 없습니다.")
        SocialPlatform socialPlatform,
        String name,
        @JsonProperty(value = "onboarding")
        OnboardingRequest onboardingRequest,
        @Valid
        @JsonProperty(value = "challenge")
        ChallengeSignUpRequest challengeSignUpRequest
) {

    public ChallengeRequest toChallengeRequest() {
        return new ChallengeRequest(challengeSignUpRequest.period(), challengeSignUpRequest.goalTime());
    }

    public OnboardingInfo toOnboardingInfo(Long userId) {
        return OnboardingInfo.builder()
                .averageUseTime(onboardingRequest.averageUseTime())
                .userId(userId)
                .build();
    }

    public List<OnboardingProblem> toProblemList(Long onboardingInfoId) {
        return onboardingRequest.problemList().stream()
                .map(problem -> OnboardingProblem.builder()
                        .onboardingInfoId(onboardingInfoId)
                        .problem(problem)
                        .build()
                ).toList();
    }
}