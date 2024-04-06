package sopt.org.hmh.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.global.auth.jwt.TokenResponse;

public record LoginResponse(
        Long userId,
        @JsonProperty(value = "token")
        TokenResponse tokenResponse
){

    public static LoginResponse of(User loginUser, TokenResponse tokenResponse) {
        return new LoginResponse(
                loginUser.getId(),
                tokenResponse
        );
    }

}