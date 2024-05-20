package sopt.org.hmh.domain.user.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sopt.org.hmh.domain.auth.dto.request.SocialSignUpRequest;
import sopt.org.hmh.domain.auth.exception.AuthError;
import sopt.org.hmh.domain.auth.exception.AuthException;
import sopt.org.hmh.domain.auth.repository.OnboardingInfoRepository;
import sopt.org.hmh.domain.auth.repository.ProblemRepository;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;
import sopt.org.hmh.domain.user.domain.OnboardingInfo;
import sopt.org.hmh.domain.user.domain.OnboardingProblem;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.domain.UserConstants;
import sopt.org.hmh.domain.user.domain.exception.UserError;
import sopt.org.hmh.domain.user.domain.exception.UserException;
import sopt.org.hmh.domain.user.dto.response.UserInfoResponse;
import sopt.org.hmh.domain.user.repository.UserRepository;
import sopt.org.hmh.global.auth.redis.RedisManagerService;
import sopt.org.hmh.global.auth.social.SocialPlatform;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final RedisManagerService redisManagerService;
    private final UserRepository userRepository;
    private final OnboardingInfoRepository onboardingInfoRepository;
    private final ProblemRepository problemRepository;


    @Transactional
    public void withdraw(Long userId) {
        redisManagerService.deleteRefreshToken(userId);
        this.findByIdOrThrowException(userId).softDelete();
    }

    public void logout(Long userId) {
        redisManagerService.deleteRefreshToken(userId);
    }

    public UserInfoResponse getUserInfo(Long userId) {
        return UserInfoResponse.of(this.findByIdOrThrowException(userId));
    }

    public User getUserBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId) {
        User user = this.findBySocialPlatformAndSocialIdOrThrowException(socialPlatform, socialId);
        if (user.isDeleted()) {
            user.recover();
        }
        return user;
    }

    public void validateDuplicateUser(String socialId, SocialPlatform socialPlatform) {
        if (userRepository.existsBySocialPlatformAndSocialId(socialPlatform, socialId)) {
            throw new AuthException(AuthError.DUPLICATE_USER);
        }
    }

    public User addUser(SocialPlatform socialPlatform, String socialId, String name) {
        return userRepository.save(
                User.builder()
                        .socialPlatform(socialPlatform)
                        .socialId(socialId)
                        .name(validateName(name))
                        .build()
        );
    }

    private String validateName(String name) {
        if (!StringUtils.hasText(name)) {
            return UserConstants.DEFAULT_USER_NAME;
        }
        return name;
    }

    public void registerOnboardingInfo(SocialSignUpRequest request) {
        OnboardingInfo onboardingInfo = OnboardingInfo.builder()
                .averageUseTime(request.onboardingRequest().averageUseTime())
                .build();
        Long onboardingInfoId = onboardingInfoRepository.save(onboardingInfo).getId();

        List<OnboardingProblem> problemList = request.onboardingRequest().problemList().stream()
                .map(problem -> OnboardingProblem.builder()
                        .onboardingInfoId(onboardingInfoId)
                        .problem(problem).build())
                .toList();
        problemRepository.saveAll(problemList);
    }

    public User findBySocialPlatformAndSocialIdOrThrowException(SocialPlatform socialPlatform, String socialId) {
        return userRepository.findBySocialPlatformAndSocialId(socialPlatform, socialId).orElseThrow(
                () -> new AuthException(AuthError.NOT_SIGNUP_USER));
    }

    public User findByIdOrThrowException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserException(UserError.NOT_FOUND_USER));
    }

    public Long getCurrentChallengeIdByUserId(Long userId) {
        return Optional.ofNullable(this.findByIdOrThrowException(userId).getCurrentChallengeId())
                .orElseThrow(() -> new UserException(UserError.NOT_FOUND_CURRENT_CHALLENGE_ID));
    }
}