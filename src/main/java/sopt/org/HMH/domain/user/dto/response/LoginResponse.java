package sopt.org.HMH.domain.user.dto.response;

import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.global.auth.jwt.TokenResponse;

public record LoginResponse(
        Long userId,
        String accessToken,
        String refreshToken
){

    public static LoginResponse of(User loginUser, TokenResponse tokenResponse) {
        return new LoginResponse(
                loginUser.getId(),
                tokenResponse.getAccessToken(),
                tokenResponse.getRefreshToken()
        );
    }

}