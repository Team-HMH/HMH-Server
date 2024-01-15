package sopt.org.HMH.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.domain.challenge.service.ChallengeService;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.domain.user.domain.UserConstants;
import sopt.org.HMH.domain.user.domain.exception.UserError;
import sopt.org.HMH.domain.user.domain.exception.UserException;
import sopt.org.HMH.domain.user.dto.request.SocialPlatformRequest;
import sopt.org.HMH.domain.user.dto.request.SocialSignUpRequest;
import sopt.org.HMH.domain.user.dto.response.LoginResponse;
import sopt.org.HMH.domain.user.dto.response.ReissueResponse;
import sopt.org.HMH.domain.user.dto.response.UserInfoResponse;
import sopt.org.HMH.domain.user.repository.UserRepository;
import sopt.org.HMH.global.auth.jwt.JwtProvider;
import sopt.org.HMH.global.auth.jwt.JwtValidator;
import sopt.org.HMH.global.auth.jwt.TokenResponse;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;
import sopt.org.HMH.global.auth.redis.TokenService;
import sopt.org.HMH.global.auth.social.SocialPlatform;
import sopt.org.HMH.global.auth.social.apple.fegin.AppleOAuthProvider;
import sopt.org.HMH.global.auth.social.kakao.fegin.KakaoLoginService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final UserRepository userRepository;
    private final KakaoLoginService kakaoLoginService;
    private final ChallengeService challengeService;
    private final TokenService tokenService;
    private final AppleOAuthProvider appleOAuthProvider;
    private final AppService appService;

    @Transactional
    public LoginResponse login(String socialAccessToken, SocialPlatformRequest request) {

        SocialPlatform socialPlatform = request.socialPlatform();
        String socialId = getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);

        // 유저를 찾지 못하면 404 Error를 던져 클라이언트에게 회원가입 api를 요구한다.
        User loginUser = getUserBySocialPlatformAndSocialId(socialPlatform, socialId);

        if (loginUser.isDeleted()) {
            loginUser.recover();
        }

        return performLogin(socialAccessToken, socialPlatform, loginUser);
    }

    @Transactional
    public LoginResponse signup(String socialAccessToken, SocialSignUpRequest request, String os) {

        SocialPlatform socialPlatform = request.socialPlatform();
        String socialId = getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);

        // 이미 회원가입된 유저가 있다면 400 Error 발생
        validateDuplicateUser(socialId, socialPlatform);

        User user = addUser(socialPlatform, socialId, request.name());

        challengeService.addChallenge(user.getId(), request.challengeSignUpRequest().period(), request.challengeSignUpRequest().goalTime());
        appService.addAppsByUserId(user.getId(), request.challengeSignUpRequest().apps(), os);

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
        User user = userRepository.findByIdOrThrowException(userId);
        user.softDelete();
    }

    public void logout(Long userId) {
        tokenService.deleteRefreshToken(userId);
    }

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findByIdOrThrowException(userId);
        return UserInfoResponse.of(user);
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
        return userRepository.findBySocialPlatformAndSocialIdOrThrowException(socialPlatform, socialId);
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
        String validSocialAccessToken = parsedTokens[1];
        return validSocialAccessToken;
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
        TokenResponse tokenResponse = jwtProvider.issueToken(loginUser.getId());
        return LoginResponse.of(loginUser, tokenResponse);
    }

    private User addUser(SocialPlatform socialPlatform, String socialId, String name) {
        User user = User.builder()
                .socialPlatform(socialPlatform)
                .socialId(socialId)
                .name(validateName(name))
                .build();
        userRepository.save(user);
        return user;
    }

    private String validateName(String name) {
        if (StringUtils.isEmpty(name)) {
            return UserConstants.DEFAULT_USER_NAME;
        }
        return name;
    }
}