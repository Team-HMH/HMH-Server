package sopt.org.HMH.global.auth.social.kakao.fegin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.global.auth.social.kakao.response.KakaoUserResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginService {

    private final KakaoApiClient kakaoApiClient;
    private static final String TOKEN_TYPE = "Bearer ";

    /**
     * 카카오 Acess Token으로 유저 Id 불러오는 함수
     */
    public Long getUserIdByKakao(String socialAccessToken) {

        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation(TOKEN_TYPE + socialAccessToken);
        System.out.println("userResponse : " + userResponse);
        return userResponse.id();
    }

    /**
     * 카카오 Access Token으로 유저 정보 업데이트
     */
    public void updateUserInfoByKakao(User loginUser, String socialAccessToken) {
        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation(TOKEN_TYPE + socialAccessToken);

        String nickname = userResponse.kakaoAccount().profile().nickname();
        String profileImageUrl = userResponse.kakaoAccount().profile().profileImageUrl();

        if (StringUtils.isEmpty(profileImageUrl)) {
            profileImageUrl = "";
        }

        loginUser.updateSocialInfo(nickname, profileImageUrl);
    }
}