package sopt.org.HMH.global.auth.social.kakao.fegin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.global.auth.social.kakao.request.KakaoUserRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginService {

    private final KakaoFeignClient kakaoFeignClient;

    /**
     * 카카오 Acess Token으로 유저의 소셜 Id 불러오는 함수
     */
    public String getSocialIdByKakao(final String socialAccessToken) {

        KakaoUserRequest userRequest = kakaoFeignClient.getUserInformation(socialAccessToken);
        return String.valueOf(userRequest.id());
    }

    /**
     * 카카오 Access Token으로 유저 정보 업데이트
     */
    public void updateUserInfoByKakao(User loginUser, final String socialAccessToken) {
        KakaoUserRequest userResponse = kakaoFeignClient.getUserInformation(socialAccessToken);

        String nickname = userResponse.kakaoAccount().profile().nickname();
        String profileImageUrl = userResponse.kakaoAccount().profile().profileImageUrl();

        if (StringUtils.isEmpty(profileImageUrl)) {
            profileImageUrl = "";
        }

        loginUser.updateSocialInfo(nickname, profileImageUrl);
    }
}