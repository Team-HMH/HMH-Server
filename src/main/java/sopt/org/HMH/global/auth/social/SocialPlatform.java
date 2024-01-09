package sopt.org.HMH.global.auth.social;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialPlatform {

    KAKAO("kakao"),
    APPLE("apple")
    ;

    private final String value;
}