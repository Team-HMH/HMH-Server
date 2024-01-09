package sopt.org.HMH.domain.user.dto.response;

import sopt.org.HMH.domain.user.domain.User;

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