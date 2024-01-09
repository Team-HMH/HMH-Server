package sopt.org.HMH.domain.user.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.user.domain.OnboardingInfo;
import sopt.org.HMH.domain.user.domain.OnboardingProblem;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.domain.user.domain.exception.UserError;
import sopt.org.HMH.domain.user.domain.exception.UserException;
import sopt.org.HMH.domain.user.dto.request.SocialPlatformRequest;
import sopt.org.HMH.domain.user.dto.request.SocialSignUpRequest;
import sopt.org.HMH.domain.user.dto.response.LoginResponse;
import sopt.org.HMH.domain.user.repository.OnboardingInfoRepository;
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
    private final OnboardingInfoRepository onboardingInfoRepository;
    private final KakaoLoginService kakaoLoginService;

    @Transactional
    public LoginResponse login(String socialAccessToken, SocialPlatformRequest request) {

        SocialPlatform socialPlatform = request.socialPlatform();
        Long socialId = getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);

        // 유저를 찾지 못하면 404 Error를 던져 클라이언트에게 회원가입 api를 요구한다.
        User loginUser = getUserBySocialPlatformAndSocialId(socialPlatform, socialId);

        return performLogin(socialAccessToken, socialPlatform, loginUser);
    }

    @Transactional
    public LoginResponse signup(String socialAccessToken, SocialSignUpRequest request) {

        SocialPlatform socialPlatform = request.socialPlatform();
        Long socialId = getSocialIdBySocialAccessToken(socialPlatform, socialAccessToken);

        // 이미 회원가입된 유저가 있다면 400 Error 발생
        validateDuplicateUser(socialId, socialPlatform);

        OnboardingInfo onboardingInfo = createOnboardingInfo(request);
        User user = createUser(socialPlatform, socialId, onboardingInfo);

        return performLogin(socialAccessToken, socialPlatform, user);
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

    private User getUserBySocialPlatformAndSocialId(SocialPlatform socialPlatform, Long socialId) {
        return userRepository.findBySocialPlatformAndSocialIdOrThrowException(socialPlatform, socialId);
    }

    private Long getSocialIdBySocialAccessToken(SocialPlatform socialPlatform, String socialAccessToken) {
        return switch (socialPlatform.toString()) {
            case "KAKAO" -> kakaoLoginService.getSocialIdByKakao(socialAccessToken);
            default -> throw new JwtException(JwtError.INVALID_SOCIAL_ACCESS_TOKEN);
        };
    }

    /**
     * 소셜 액세스 토큰에서 "Bearer " 부분을 삭제시키고 유효한 소셜 액세스 토큰만을 받기 위한 함수
     */
    private String parseTokenString(String tokenString) {
        String[] parsedTokens = tokenString.split(" ");
        if (parsedTokens.length != 2) {
            throw new JwtException(JwtError.INVALID_TOKEN_HEADER);
        }
        String validSocialAccessToken = parsedTokens[1];
        return validSocialAccessToken;
    }

    private void validateDuplicateUser(Long socialId, SocialPlatform socialPlatform) {
        if (userRepository.existsBySocialPlatformAndSocialId(socialPlatform, socialId)) {
            throw new UserException(UserError.DUPLICATE_USER);
        }
    }

    private LoginResponse performLogin(String socialAccessToken, SocialPlatform socialPlatform, User loginUser) {
        if (socialPlatform == SocialPlatform.KAKAO) {
            kakaoLoginService.updateUserInfoByKakao(loginUser, socialAccessToken);
        }
        TokenDto tokenDto = jwtProvider.issueToken(new UserAuthentication(loginUser.getId(), null, null));
        return LoginResponse.of(loginUser, tokenDto);
    }

    private User createUser(SocialPlatform socialPlatform, Long socialId, OnboardingInfo onboardingInfo) {
        User user = User.builder()
                .socialPlatform(socialPlatform)
                .socialId(socialId)
                .onboardingInfo(onboardingInfo)
                .build();
        userRepository.save(user);
        return user;
    }

    private OnboardingInfo createOnboardingInfo(SocialSignUpRequest request) {
        List<OnboardingProblem> problemList = new ArrayList<>();
        for (String problem : request.onboardingRequest().problemList()) {
            problemList.add(
                    OnboardingProblem.builder()
                            .problem(problem)
                            .build()
            );
        }

        OnboardingInfo onboardingInfo = OnboardingInfo.builder()
                .averageUseTime(request.onboardingRequest().averageUseTime())
                .problem(problemList)
                .build();
        onboardingInfoRepository.save(onboardingInfo);
        return onboardingInfo;
    }
}