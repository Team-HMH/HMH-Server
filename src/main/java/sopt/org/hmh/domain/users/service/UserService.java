package sopt.org.hmh.domain.users.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sopt.org.hmh.domain.auth.dto.request.SocialSignUpRequest;
import sopt.org.hmh.domain.auth.exception.AuthError;
import sopt.org.hmh.domain.auth.exception.AuthException;
import sopt.org.hmh.domain.auth.repository.OnboardingInfoRepository;
import sopt.org.hmh.domain.auth.repository.ProblemRepository;
import sopt.org.hmh.domain.users.domain.OnboardingInfo;
import sopt.org.hmh.domain.users.domain.OnboardingProblem;
import sopt.org.hmh.domain.users.domain.User;
import sopt.org.hmh.domain.users.domain.UserConstants;
import sopt.org.hmh.domain.users.dto.response.UserInfoResponse;
import sopt.org.hmh.domain.users.repository.UserRepository;
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
        userRepository.findByIdOrThrowException(userId).softDelete();
    }

    public void logout(Long userId) {
        redisManagerService.deleteRefreshToken(userId);
    }

    public UserInfoResponse getUserInfo(Long userId) {
        return UserInfoResponse.of(userRepository.findByIdOrThrowException(userId));
    }

    public User getUserBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId) {
        User user = userRepository.findBySocialPlatformAndSocialIdOrThrowException(socialPlatform, socialId);
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

}
