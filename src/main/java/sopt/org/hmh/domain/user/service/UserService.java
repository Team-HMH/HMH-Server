package sopt.org.hmh.domain.user.service;

import java.time.LocalDate;
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
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.domain.UserConstants;
import sopt.org.hmh.domain.user.domain.exception.UserError;
import sopt.org.hmh.domain.user.domain.exception.UserException;
import sopt.org.hmh.domain.user.dto.response.UserResponse.IsLockTodayResponse;
import sopt.org.hmh.domain.user.dto.response.UserResponse.UserInfoResponse;
import sopt.org.hmh.domain.user.repository.UserRepository;
import sopt.org.hmh.global.auth.social.SocialPlatform;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OnboardingInfoRepository onboardingInfoRepository;
    private final ProblemRepository problemRepository;

    @Transactional
    public void withdraw(Long userId) {
        this.findByIdOrThrowException(userId).softDelete();
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        return UserInfoResponse.of(this.findByIdOrThrowException(userId));
    }

    public User getUserBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId) {
        return this.findBySocialPlatformAndSocialIdOrThrowException(socialPlatform, socialId);
    }

    public void recoverIfIsDeletedUser(User user) {
        if (user.isDeleted()) {
            user.recover();
        }
    }

    public void validateDuplicateUser(String socialId, SocialPlatform socialPlatform) {
        if (userRepository.existsBySocialPlatformAndSocialId(socialPlatform, socialId)) {
            throw new AuthException(AuthError.DUPLICATE_USER);
        }
    }

    public User addUser(SocialPlatform socialPlatform, String socialId, String name) {
        this.validateDuplicateUser(socialId, socialPlatform);

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

    public void registerOnboardingInfo(SocialSignUpRequest request, Long userId) {
        Long onboardingInfoId = onboardingInfoRepository.save(request.toOnboardingInfo(userId)).getId();
        problemRepository.saveAll(request.toProblemList(onboardingInfoId));
    }

    private User findBySocialPlatformAndSocialIdOrThrowException(SocialPlatform socialPlatform, String socialId) {
        return userRepository.findBySocialPlatformAndSocialId(socialPlatform, socialId).orElseThrow(
                () -> new AuthException(AuthError.NOT_SIGNUP_USER));
    }

    public User findByIdOrThrowException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserException(UserError.NOT_FOUND_USER));
    }

    private boolean isExistUserId(Long userId) {
        return userRepository.existsById(userId);
    }

    public void checkIsExistUserId(Long userId) {
        if (!isExistUserId(userId)) {
            throw new UserException(UserError.NOT_FOUND_USER);
        }
    }

    public Long getCurrentChallengeIdByUserId(Long userId) {
        return Optional.ofNullable(this.findByIdOrThrowException(userId).getCurrentChallengeId())
                .orElseThrow(() -> new UserException(UserError.NOT_FOUND_CURRENT_CHALLENGE_ID));
    }

    public void changeCurrentChallengeIdByUserId(Long userId, Long challengeId) {
        this.findByIdOrThrowException(userId).changeCurrentChallengeId(challengeId);
    }

    @Transactional
    public void changeRecentLockDate(Long userId, LocalDate lockDate) {
        this.findByIdOrThrowException(userId).changeRecentLockDate(lockDate);
    }

    @Transactional(readOnly = true)
    public IsLockTodayResponse checkIsTodayLock(Long userId, LocalDate lockCheckDate) {
        LocalDate userRecentLockDate = this.findByIdOrThrowException(userId).getRecentLockDate();
        return new IsLockTodayResponse(lockCheckDate.equals(userRecentLockDate));
    }

    public void withdrawImmediately(Long userId) {
        userRepository.deleteById(userId);
    }
}