package sopt.org.hmh.global.auth.jwt.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}