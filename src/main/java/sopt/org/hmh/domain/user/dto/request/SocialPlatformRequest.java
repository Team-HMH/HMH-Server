package sopt.org.hmh.domain.user.dto.request;

import sopt.org.hmh.global.auth.social.SocialPlatform;

public record SocialPlatformRequest(
        SocialPlatform socialPlatform
) {
}