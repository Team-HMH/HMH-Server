package sopt.org.hmh.domain.user.dto.response;

import sopt.org.hmh.domain.user.domain.User;

public record UserPointResponse(
        Integer point
) {
    public static UserPointResponse of(User user) {
        return new UserPointResponse(
                user.getPoint()
        );
    }
}