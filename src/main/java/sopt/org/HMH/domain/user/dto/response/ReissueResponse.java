package sopt.org.HMH.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.org.HMH.global.auth.jwt.TokenResponse;

public record ReissueResponse(
        @JsonProperty(value = "token")
        TokenResponse tokenResponse
) {
    public static ReissueResponse of(TokenResponse tokenResponse) {
        return new ReissueResponse(
                tokenResponse
        );
    }
}
