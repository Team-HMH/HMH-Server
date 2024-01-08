package sopt.org.HMH.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.global.auth.jwt.TokenDto;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private Long userId;

    private TokenDto tokenDto;

    public static LoginResponse of(User loginUser, TokenDto tokenDto) {

        return new LoginResponse(loginUser.getId(), tokenDto);
    }
}