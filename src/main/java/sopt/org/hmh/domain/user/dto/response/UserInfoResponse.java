package sopt.org.hmh.domain.user.dto.response;

import sopt.org.hmh.domain.user.domain.User;

public record UserInfoResponse(
        String name,
        Integer point
) {
    public static UserInfoResponse of(User user) {
        return new UserInfoResponse(
                user.getName(),
                user.getPoint()
        );
    }
}