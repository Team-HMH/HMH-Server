package sopt.org.hmh.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.service.ChallengeAppService;
import sopt.org.hmh.domain.auth.dto.response.ReissueResponse;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.service.ChallengeFacade;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.auth.dto.request.SocialPlatformRequest;
import sopt.org.hmh.domain.auth.dto.request.SocialSignUpRequest;
import sopt.org.hmh.domain.auth.dto.response.LoginResponse;
import sopt.org.hmh.domain.user.service.UserService;
import sopt.org.hmh.global.auth.jwt.TokenService;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;
import sopt.org.hmh.global.auth.social.SocialPlatform;
import sopt.org.hmh.global.auth.social.SocialAccessTokenResponse;
import sopt.org.hmh.global.auth.social.apple.fegin.AppleOAuthProvider;
import sopt.org.hmh.global.auth.social.kakao.fegin.KakaoLoginService;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final KakaoLoginService kakaoLoginService;
    private final AppleOAuthProvider appleOAuthProvider;
    private final ChallengeFacade challengeFacade;
    private final ChallengeAppService challengeAppService;
    private final TokenService tokenService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public LoginResponse login(String socialAccessToken, SocialPlatformRequest request) {
        SocialPlatform socialPlatform = request.socialPlatform();
        String socialId = this.getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);
        User loginUser = userService.getUserBySocialPlatformAndSocialId(socialPlatform, socialId);

        return performLogin(socialAccessToken, socialPlatform, loginUser);
    }

    @Transactional
    public LoginResponse signup(String socialAccessToken, SocialSignUpRequest request, String os) {
        SocialPlatform socialPlatform = request.socialPlatform();
        String socialId = this.getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);

        userService.validateDuplicateUser(socialId, socialPlatform);
        User user = userService.addUser(socialPlatform, socialId, request.name());
        Long userId = user.getId();
        userService.registerOnboardingInfo(request, userId);

        Challenge challenge = challengeFacade.addChallenge(userId, request.toChallengeRequest() , os);
        challengeAppService.addApps(challenge, request.challengeSignUpRequest().apps(), os);

        return performLogin(socialAccessToken, socialPlatform, user);
    }

    private String getSocialIdBySocialAccessToken(SocialPlatform socialPlatform, String socialAccessToken) {
        return switch (socialPlatform.toString()) {
            case "KAKAO" -> kakaoLoginService.getSocialIdByKakao(socialAccessToken);
            case "APPLE" -> appleOAuthProvider.getApplePlatformId(socialAccessToken);
            default -> throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN);
        };
    }

    public ReissueResponse reissueToken(String refreshToken) {
        return tokenService.reissueToken(refreshToken);
    }

    private LoginResponse performLogin(String socialAccessToken, SocialPlatform socialPlatform, User loginUser) {
        if (socialPlatform == SocialPlatform.KAKAO) {
            kakaoLoginService.updateUserInfoByKakao(loginUser, socialAccessToken);
        }
        Long userId = loginUser.getId();
        return new LoginResponse(userId, tokenService.issueToken(userId));
    }

    public SocialAccessTokenResponse getSocialAccessTokenByAuthorizationCode(String code) {
        return kakaoLoginService.getKakaoAccessToken(code);
    }
}