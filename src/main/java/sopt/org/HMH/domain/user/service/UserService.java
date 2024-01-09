package sopt.org.HMH.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.user.domain.OnboardingInfo;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.domain.user.domain.exception.UserError;
import sopt.org.HMH.domain.user.domain.exception.UserException;
import sopt.org.HMH.domain.user.dto.request.SocialPlatformRequest;
import sopt.org.HMH.domain.user.dto.request.SocialSignUpRequest;
import sopt.org.HMH.domain.user.dto.response.LoginResponse;
import sopt.org.HMH.domain.user.repository.UserRepository;
import sopt.org.HMH.global.auth.jwt.JwtProvider;
import sopt.org.HMH.global.auth.jwt.TokenDto;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;
import sopt.org.HMH.global.auth.security.UserAuthentication;
import sopt.org.HMH.global.auth.social.SocialPlatform;
import sopt.org.HMH.global.auth.social.kakao.fegin.KakaoLoginService;

@Service
@RequiredArgsConstructor // final 필드를 가지는 생성자를 자동으로 생성해주는 어노테이션
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final KakaoLoginService kakaoLoginService;

    @Transactional
    public LoginResponse login(String socialAccessToken, SocialPlatformRequest request) {

        socialAccessToken = parseTokenString(socialAccessToken);
        SocialPlatform socialPlatform = request.socialPlatform();
        Long socialId = getUserIdBySocialAccessToken(socialPlatform, socialAccessToken);

        // 유저를 찾지 못하면 404 Error를 던져 클라이언트에게 회원가입 api를 요구한다.
        User loginUser = getUserBySocialAndSocialId(socialPlatform, socialId);

        if (socialPlatform == SocialPlatform.KAKAO) {
            kakaoLoginService.updateUserInfoByKakao(loginUser, socialAccessToken);
        }

        TokenDto tokenDto = jwtProvider.issueToken(new UserAuthentication(loginUser.getId(), null, null));

        return LoginResponse.of(loginUser, tokenDto);
    }

    @Transactional
    public LoginResponse signup(String socialAccessToken, SocialSignUpRequest request) {

        socialAccessToken = parseTokenString(socialAccessToken);
        SocialPlatform socialPlatform = request.socialPlatformRequest().socialPlatform();
        Long socialId = getUserIdBySocialAccessToken(socialPlatform, socialAccessToken);

        OnboardingInfo onboardingInfo = OnboardingInfo.builder()
                .averageUseTime(request.onboardingRequest().averageUseTime())
                .problem(request.onboardingRequest().problem())
                .build();

        User user = User.builder()
                .socialPlatform(socialPlatform)
                .socialId(socialId)
                .onboardingInfo(onboardingInfo)
                .build();

        userRepository.save(user);

        TokenDto tokenDto = jwtProvider.issueToken(new UserAuthentication(user.getId(), null, null));

        return LoginResponse.of(user, tokenDto);
    }

    @Transactional
    public TokenDto reissueToken(String refreshToken) {

        refreshToken = parseTokenString(refreshToken);

        Long userId = jwtProvider.validateRefreshToken(refreshToken);
        validateUserId(userId);  // userId가 DB에 저장된 유효한 값인지 검사

        jwtProvider.deleteRefreshToken(userId);
        return jwtProvider.issueToken(new UserAuthentication(userId, null, null));
    }

    @Transactional
    public void logout(Long userId) {
        jwtProvider.deleteRefreshToken(userId);
    }

    private void validateUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserException(UserError.NOT_FOUND_USER);
        }
    }

    private User getUserBySocialAndSocialId(SocialPlatform socialPlatform, Long socialId) {
        return userRepository.findBySocialPlatformAndSocialId(socialPlatform, socialId)
                .orElseThrow(() -> new UserException(UserError.NOT_SIGNUP_USER));
    }

    private Long getUserIdBySocialAccessToken(SocialPlatform socialPlatform, String socialAccessToken) {
        return switch (socialPlatform.toString()) {
            case "KAKAO" -> kakaoLoginService.getUserIdByKakao(socialAccessToken);
            default -> throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN);
        };
    }

    private static String parseTokenString(String tokenString) {
        String[] strings = tokenString.split(" ");
        if (strings.length != 2) {
            throw new JwtException(JwtError.INVALID_TOKEN_HEADER);
        }
        return strings[1];
    }
}