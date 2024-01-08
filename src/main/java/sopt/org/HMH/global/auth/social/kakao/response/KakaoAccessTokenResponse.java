package sopt.org.HMH.global.auth.social.kakao.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoAccessTokenResponse(
        String accessToken,
        String refreshToken
) {

    public static KakaoAccessTokenResponse of(String accessToken, String refreshToken) {
        return new KakaoAccessTokenResponse(accessToken, refreshToken);
    }
}