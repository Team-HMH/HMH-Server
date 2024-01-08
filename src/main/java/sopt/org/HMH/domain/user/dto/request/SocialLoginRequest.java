package sopt.org.HMH.domain.user.dto.request;

import sopt.org.HMH.global.auth.social.SocialPlatform;

public record SocialLoginRequest(
        SocialPlatform socialPlatform
) {
}