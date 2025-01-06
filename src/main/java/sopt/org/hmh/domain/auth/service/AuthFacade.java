package sopt.org.hmh.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.auth.dto.response.ReissueResponse;
import sopt.org.hmh.domain.challenge.dto.request.NewChallengeOrder;
import sopt.org.hmh.domain.challenge.service.ChallengeFacade;
import sopt.org.hmh.domain.slack.builder.NewUserSlackMessageBuilder;
import sopt.org.hmh.domain.slack.constant.SlackStatus;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.auth.dto.request.SocialSignUpRequest;
import sopt.org.hmh.domain.auth.dto.response.LoginResponse;
import sopt.org.hmh.domain.user.service.UserService;
import sopt.org.hmh.global.auth.jwt.service.TokenService;
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
    private final TokenService tokenService;
    private final UserService userService;
    private final NewUserSlackMessageBuilder newUserSlackMessageBuilder;

    @Transactional(readOnly = true)
    public LoginResponse login(String socialAccessToken, SocialPlatform socialPlatform) {
        String socialId = this.getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);
        User loginUser = userService.getUserBySocialPlatformAndSocialId(socialPlatform, socialId);

        return performLogin(loginUser, socialAccessToken, socialPlatform);
    }

    @Transactional
    public LoginResponse signup(SocialSignUpRequest request, String socialAccessToken, String os, String timeZone) {
        SocialPlatform socialPlatform = request.socialPlatform();
        String socialId = this.getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);

        User newUser = userService.addUser(socialPlatform, socialId, request.name());
        Long newUserId = newUser.getId();

        userService.registerOnboardingInfo(request, newUserId);

        challengeFacade.startNewChallenge(NewChallengeOrder.createFirstChallengeOrder(
                request.challenge().toChallengeRequest(), request.challenge().apps(),
                newUserId, os, timeZone
        ));

        newUserSlackMessageBuilder.sendNotification(SlackStatus.NEW_USER_SIGNUP, request.name(), os);

        return performLogin(newUser, socialAccessToken, socialPlatform);
    }

    private String getSocialIdBySocialAccessToken(SocialPlatform socialPlatform, String socialAccessToken) {
        if (socialPlatform == SocialPlatform.APPLE) {
            return appleOAuthProvider.getApplePlatformId(socialAccessToken);
        }
        if (socialPlatform == SocialPlatform.KAKAO) {
            return kakaoLoginService.getSocialIdByKakao(socialAccessToken);
        }
        throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN);
    }

    private LoginResponse performLogin(User loginUser, String socialAccessToken, SocialPlatform socialPlatform) {
        this.updateAdditionalUserLoginInfo(loginUser, socialAccessToken, socialPlatform);

        Long userId = loginUser.getId();
        return new LoginResponse(userId, tokenService.issueToken(userId.toString()));
    }

    private void updateAdditionalUserLoginInfo(User loginUser, String socialAccessToken, SocialPlatform socialPlatform) {
        userService.recoverIfIsDeletedUser(loginUser);

        if (socialPlatform == SocialPlatform.KAKAO) {
            kakaoLoginService.updateUserInfoByKakao(loginUser, socialAccessToken);
        }
    }


    public ReissueResponse reissueToken(String refreshToken) {
        return tokenService.reissueToken(refreshToken);
    }

    public SocialAccessTokenResponse getSocialAccessTokenByAuthorizationCode(String code) {
        return kakaoLoginService.getKakaoAccessToken(code);
    }
}