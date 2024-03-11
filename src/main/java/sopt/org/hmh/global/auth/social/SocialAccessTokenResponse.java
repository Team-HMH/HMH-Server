package sopt.org.hmh.global.auth.social;

import lombok.Builder;

@Builder
public record SocialAccessTokenResponse(
        String socialAccessToken
) {
}
