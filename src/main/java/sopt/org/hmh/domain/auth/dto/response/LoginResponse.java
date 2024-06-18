package sopt.org.hmh.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.org.hmh.global.auth.jwt.TokenResponse;

public record LoginResponse(
        Long userId,
        @JsonProperty(value = "token")
        TokenResponse tokenResponse
){
}