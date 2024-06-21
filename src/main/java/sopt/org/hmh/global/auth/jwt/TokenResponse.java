package sopt.org.hmh.global.auth.jwt;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}