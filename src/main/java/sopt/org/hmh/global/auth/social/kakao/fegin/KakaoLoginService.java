package sopt.org.hmh.global.auth.social.kakao.fegin;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;
import sopt.org.hmh.global.auth.social.SocialAccessTokenResponse;
import sopt.org.hmh.global.auth.social.kakao.response.KakaoTokenResponse;
import sopt.org.hmh.global.auth.social.kakao.response.KakaoUserResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginService {

    private final KakaoFeignClient kakaoFeignClient;
    private final KakaoAuthFeignClient kakaoAuthFeignClient;

    @Value("${oauth2.kakao.client-id}")
    private String CLIENT_ID;
    @Value("${oauth2.kakao.authorization-grant-type}")
    private String GRANT_TYPE;
    @Value("${oauth2.kakao.redirect-uri}")
    private String REDIRECT_URL;

    public String getSocialIdByKakao(final String socialAccessToken) {
        return String.valueOf(getKakaoUserRequest(socialAccessToken).id());
    }

    public void updateUserInfoByKakao(User loginUser, final String socialAccessToken) {
        KakaoUserResponse userRequest = getKakaoUserRequest(socialAccessToken);

        try {
            loginUser.updateNickname(userRequest.kakaoAccount().profile().nickname());
        } catch (NullPointerException exception) {
            throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN_FORMAT);
        }
    }

    private KakaoUserResponse getKakaoUserRequest(final String socialAccessToken) {
        try {
            return kakaoFeignClient.getUserInformation(socialAccessToken);
        } catch (FeignException exception) {
            throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN);
        }
    }

    public SocialAccessTokenResponse getKakaoAccessToken(String code) {

        KakaoTokenResponse kakaoTokenResponse = kakaoAuthFeignClient.getOAuth2AccessToken(
                GRANT_TYPE,
                CLIENT_ID,
                REDIRECT_URL,
                code
        );

        return SocialAccessTokenResponse.builder()
                .socialAccessToken(kakaoTokenResponse.accessToken())
                .build();
    }
}