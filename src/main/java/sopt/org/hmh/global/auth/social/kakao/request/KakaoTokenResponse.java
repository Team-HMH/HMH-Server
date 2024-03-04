package sopt.org.hmh.global.auth.social.kakao.request;

public record KakaoTokenResponse(
        String accessToken,
        String refreshToken
) {
}
