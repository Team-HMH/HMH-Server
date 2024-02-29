package sopt.org.hmh.global.auth.social.kakao.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUserRequest(
        Long id,
        KakaoAccount kakaoAccount
) {
}