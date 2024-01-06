package sopt.org.HMH.global.auth.social.kakao.fegin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.global.auth.social.kakao.response.KakaoUserResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginService {

    private KakaoApiClient kakaoApiClient;
    private static final String TOKEN_TYPE = "Bearer: ";

    public String getKakaoId(String socialAccessToken) {

        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation(TOKEN_TYPE + socialAccessToken);

        return Long.toString(userResponse.getId());
    }

    public void setKakaoInfo(User loginUser, String socialAccessToken) {

        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation(TOKEN_TYPE + socialAccessToken);

        loginUser.updateSocialInfo(userResponse.getKakaoAccount().getProfile().getNickname(),
                userResponse.getKakaoAccount().getProfile().getProfileImageUrl()); //카카오 Access 토큰도 매번 업데이트
    }

}