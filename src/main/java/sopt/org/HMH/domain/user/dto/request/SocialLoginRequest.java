package sopt.org.HMH.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.global.auth.social.SocialPlatform;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialLoginRequest {

    private SocialPlatform socialPlatform;
}