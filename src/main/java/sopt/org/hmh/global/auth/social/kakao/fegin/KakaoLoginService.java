package sopt.org.hmh.global.auth.social.kakao.fegin;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.auth.jwt.exception.JwtException;
import sopt.org.hmh.global.auth.social.kakao.request.KakaoUserRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginService {

    private final KakaoFeignClient kakaoFeignClient;

    public String getSocialIdByKakao(final String socialAccessToken) {
        return String.valueOf(getKakaoUserRequest(socialAccessToken).id());
    }

    public void updateUserInfoByKakao(User loginUser, final String socialAccessToken) {
        KakaoUserRequest userRequest = getKakaoUserRequest(socialAccessToken);

        try {
            String nickname = userRequest.kakaoAccount().profile().nickname();
            String profileImageUrl = userRequest.kakaoAccount().profile().profileImageUrl();

            if (!StringUtils.hasText(profileImageUrl)) {
                profileImageUrl = "";
            }

            loginUser.updateSocialInfo(nickname, profileImageUrl);

        } catch (NullPointerException exception) {
            throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN_FORMAT);
        }
    }

    private KakaoUserRequest getKakaoUserRequest(final String socialAccessToken) {
        try {
            return kakaoFeignClient.getUserInformation(socialAccessToken);
        } catch (FeignException exception) {
            throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN);
        }
    }
}