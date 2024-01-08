package sopt.org.HMH.global.auth.social.kakao.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoAccessTokenResponse {

    private String accessToken;
    private String refreshToken;

    public static KakaoAccessTokenResponse of(String accessToken, String refreshToken) {
        return new KakaoAccessTokenResponse(accessToken, refreshToken);
    }
}