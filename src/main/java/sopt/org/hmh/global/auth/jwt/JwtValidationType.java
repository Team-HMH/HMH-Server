package sopt.org.hmh.global.auth.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum JwtValidationType {

    VALID_JWT("유효한 JWT 토큰입니다."),
    INVALID_JWT_SIGNATURE("잘못된 JWT 서명"),
    INVALID_JWT("유효하지 않는 JWT"),
    EXPIRED_JWT("만료된 JWT"),
    UNSUPPORTED_JWT("지원하지 않는 JWT"),
    EMPTY_JWT("JWT 토큰이 존재하지 않습니다.");

    private final String value;
}