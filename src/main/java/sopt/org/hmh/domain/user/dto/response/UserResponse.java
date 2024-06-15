package sopt.org.hmh.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

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

    public record IsLockTodayResponse(
            boolean isLockToday
    ) {
    }
}
