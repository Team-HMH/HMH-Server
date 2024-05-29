package sopt.org.hmh.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.org.hmh.global.auth.jwt.TokenResponse;

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
