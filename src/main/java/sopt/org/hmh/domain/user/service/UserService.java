package sopt.org.hmh.domain.user.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.domain.user.domain.OnboardingInfo;
import sopt.org.hmh.domain.user.domain.OnboardingProblem;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.domain.UserConstants;
import sopt.org.hmh.domain.user.domain.exception.UserError;
import sopt.org.hmh.domain.user.domain.exception.UserException;
import sopt.org.hmh.domain.user.dto.request.SocialPlatformRequest;
import sopt.org.hmh.domain.user.dto.request.SocialSignUpRequest;
import sopt.org.hmh.domain.user.dto.response.LoginResponse;
import sopt.org.hmh.domain.user.dto.response.ReissueResponse;
import sopt.org.hmh.domain.user.dto.response.UserInfoResponse;
import sopt.org.hmh.domain.user.repository.OnboardingInfoRepository;
import sopt.org.hmh.domain.user.repository.ProblemRepository;
import sopt.org.hmh.domain.user.repository.UserRepository;
import sopt.org.hmh.global.auth.jwt.JwtProvider;
import sopt.org.hmh.global.auth.jwt.JwtValidator;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;
import sopt.org.hmh.global.auth.redis.TokenService;
import sopt.org.hmh.global.auth.social.SocialPlatform;
import sopt.org.hmh.global.auth.social.SocialAccessTokenResponse;
import sopt.org.hmh.global.auth.social.apple.fegin.AppleOAuthProvider;
import sopt.org.hmh.global.auth.social.kakao.fegin.KakaoLoginService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final UserRepository userRepository;
    private final OnboardingInfoRepository onboardingInfoRepository;
    private final ProblemRepository problemRepository;
    private final KakaoLoginService kakaoLoginService;
    private final ChallengeService challengeService;
    private final TokenService tokenService;
    private final AppleOAuthProvider appleOAuthProvider;

    @Transactional
    public LoginResponse login(String socialAccessToken, SocialPlatformRequest request) {

        SocialPlatform socialPlatform = request.socialPlatform();
        String socialId = getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);

        User loginUser = getUserBySocialPlatformAndSocialId(socialPlatform, socialId);

        return performLogin(socialAccessToken, socialPlatform, loginUser);
    }

    @Transactional
    public LoginResponse signup(String socialAccessToken, SocialSignUpRequest request, String os) {

        SocialPlatform socialPlatform = request.socialPlatform();
        String socialId = getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);

        validateDuplicateUser(socialId, socialPlatform);

        User user = addUser(socialPlatform, socialId, request.name());

        challengeService.updateChallengeForPeriodWithInfo(
                challengeService.addChallenge(user.getId(),
                        request.challengeSignUpRequest().period(),
                        request.challengeSignUpRequest().goalTime()),
                request.challengeSignUpRequest().apps(),
                os);
        registerOnboardingInfo(request);

        return performLogin(socialAccessToken, socialPlatform, user);
    }

    @Transactional
    public ReissueResponse reissueToken(String refreshToken) {
        refreshToken = parseTokenString(refreshToken);
        Long userId = jwtProvider.getSubject(refreshToken);
        validateRefreshToken(refreshToken, userId);
        tokenService.deleteRefreshToken(userId);
        return ReissueResponse.of(jwtProvider.issueToken(userId));
    }

    @Transactional
    public void withdraw(Long userId) {
        tokenService.deleteRefreshToken(userId);
        userRepository.findByIdOrThrowException(userId).softDelete();
    }

    public void logout(Long userId) {
        tokenService.deleteRefreshToken(userId);
    }

    public UserInfoResponse getUserInfo(Long userId) {
        return UserInfoResponse.of(userRepository.findByIdOrThrowException(userId));
    }

    private void validateUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserException(UserError.NOT_FOUND_USER);
        }
    }

    private void validateRefreshToken(String refreshToken, Long userId) {
        try {
            jwtValidator.validateRefreshToken(refreshToken);
            validateUserId(userId);
        } catch (JwtException jwtException) {
            logout(userId);
            throw jwtException;
        }
    }

    private User getUserBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId) {
        User user = userRepository.findBySocialPlatformAndSocialIdOrThrowException(socialPlatform, socialId);
        if (user.isDeleted()) {
            user.recover();
        }
        return user;
    }

    private String getSocialIdBySocialAccessToken(SocialPlatform socialPlatform, String socialAccessToken) {
        return switch (socialPlatform.toString()) {
            case "KAKAO" -> kakaoLoginService.getSocialIdByKakao(socialAccessToken);
            case "APPLE" -> appleOAuthProvider.getApplePlatformId(socialAccessToken);
            default -> throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN);
        };
    }

    /**
     * 소셜 액세스 토큰에서 "Bearer " 부분을 삭제시키고 유효한 소셜 액세스 토큰만을 받기 위한 함수
     */
    private String parseTokenString(String tokenString) {
        String[] parsedTokens = tokenString.split(" ");
        if (parsedTokens.length != 2) {
            throw new JwtException(JwtError.INVALID_TOKEN_HEADER);
        }
        return parsedTokens[1];
    }

    private void validateDuplicateUser(String socialId, SocialPlatform socialPlatform) {
        if (userRepository.existsBySocialPlatformAndSocialId(socialPlatform, socialId)) {
            throw new UserException(UserError.DUPLICATE_USER);
        }
    }

    private LoginResponse performLogin(String socialAccessToken, SocialPlatform socialPlatform, User loginUser) {
        if (socialPlatform == SocialPlatform.KAKAO) {
            kakaoLoginService.updateUserInfoByKakao(loginUser, socialAccessToken);
        }
        return LoginResponse.of(loginUser, jwtProvider.issueToken(loginUser.getId()));
    }

    private User addUser(SocialPlatform socialPlatform, String socialId, String name) {
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

    private void registerOnboardingInfo(SocialSignUpRequest request) {
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

    public void deleteExpiredUser(LocalDateTime currentDate) {
        List<Long> expiredUserList = userRepository.findIdByDeletedAtBeforeAndIsDeletedIsTrue(currentDate);
        userRepository.deleteAllById(expiredUserList);
        challengeService.deleteChallengeRelatedByUserId(expiredUserList);
    }

    public SocialAccessTokenResponse getSocialAccessTokenByAuthorizationCode(String code) {
        return kakaoLoginService.getKakaoAccessToken(code);
    }

}