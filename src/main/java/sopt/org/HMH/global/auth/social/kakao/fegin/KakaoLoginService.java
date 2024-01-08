package sopt.org.HMH.global.auth.social.kakao.fegin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return userResponse.getId();
    }

    /**
     * 카카오 Access Token으로 유저 정보 업데이트
     */
    public void updateUserInfoByKakao(User loginUser, String socialAccessToken) {
        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation(TOKEN_TYPE + socialAccessToken);
        loginUser.updateSocialInfo(userResponse.getKakaoAccount().getProfile().getNickname(),
                userResponse.getKakaoAccount().getProfile().getProfileImageUrl());
    }

}