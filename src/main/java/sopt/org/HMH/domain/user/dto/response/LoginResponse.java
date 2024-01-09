package sopt.org.HMH.domain.user.dto.response;

import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.global.auth.jwt.TokenDto;

public record LoginResponse(
        Long userId,
        String accessToken,
        String refreshToken
){

    public static LoginResponse of(User loginUser, TokenDto tokenDto) {
        return new LoginResponse(
                loginUser.getId(),
                tokenDto.getAccessToken(),
                tokenDto.getRefreshToken()
        );
    }

}