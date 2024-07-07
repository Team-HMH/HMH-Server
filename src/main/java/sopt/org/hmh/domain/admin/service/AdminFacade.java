package sopt.org.hmh.domain.admin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.admin.dto.request.AdminDailyChallengeRequest;
import sopt.org.hmh.domain.admin.dto.request.AdminUserInfoRequest;
import sopt.org.hmh.domain.admin.dto.response.AdminTokenResponse;
import sopt.org.hmh.domain.admin.exception.AdminError;
import sopt.org.hmh.domain.admin.exception.AdminException;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.service.UserService;
import sopt.org.hmh.global.auth.jwt.TokenService;

@Service
@RequiredArgsConstructor
public class AdminFacade {

    @Value("${jwt.admin-auth-code}")
    private String adminAuthCode;

    private final UserService userService;
    private final TokenService tokenService;
    private final ChallengeService challengeService;
    private final DailyChallengeService dailyChallengeService;

    public AdminTokenResponse adminLogin(String authCode) {
        validateAdminAuthCode(authCode);
        return new AdminTokenResponse(tokenService.issueAdminToken());
    }

    private void validateAdminAuthCode(String authCode) {
        if (!adminAuthCode.equals(authCode)) {
            throw new AdminException(AdminError.INVALID_ADMIN_AUTH_CODE);
        }
    }

    @Transactional
    public void withdrawImmediately(Long userId) {
        userService.checkIsExistUserId(userId);
        challengeService.deleteChallengeRelatedByUserId(userId);
        userService.withdrawImmediately(userId);
    }

    @Transactional
    public void changeUserInfo(AdminUserInfoRequest request) {
        User user = userService.findByIdOrThrowException(request.userId());
        if (Objects.nonNull(request.point())) {
            user.changePoint(request.point());
        }
        if (Objects.nonNull(request.name())) {
            user.changeName(request.name());
        }
    }

    @Transactional
    public void changeDailyChallengeInfo(AdminDailyChallengeRequest request) {
        Long currentChallengeId = userService.getCurrentChallengeIdByUserId(request.userId());
        List<Status> statuses = request.statuses();
        LocalDate challengeDate = request.startDate();

        validateStatusesPeriod(currentChallengeId, statuses);
        dailyChallengeService.changeInfoOfDailyChallenges(currentChallengeId, statuses, challengeDate);
    }

    private void validateStatusesPeriod(Long challengeId, List<Status> statuses) {
        Integer challengePeriod = challengeService.getChallengePeriod(challengeId);
        if (challengePeriod != statuses.size()) {
            throw new ChallengeException(ChallengeError.INVALID_PERIOD_NUMERIC);
        }
    }
}
