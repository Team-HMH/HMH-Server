package sopt.org.HMH.global.auth.social.kakao.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUserRequest(
        Long id,
        KakaoAccount kakaoAccount
) {
}