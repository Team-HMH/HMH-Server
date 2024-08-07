package sopt.org.hmh.domain.point.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.domain.point.dto.response.*;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class PointFacade {

    private final UserService userService;
    private final DailyChallengeService dailyChallengeService;
    private final ChallengeService challengeService;

    @Transactional
    @Deprecated
    public UsePointResponse usePointAndChallengeFailedDeprecated(Long userId, LocalDate challengeDate) {
        DailyChallenge dailyChallenge =
                dailyChallengeService.findDailyChallengeByChallengeDateAndUserIdOrElseThrow(challengeDate, userId);
        User user = userService.findByIdOrThrowException(userId);

        dailyChallengeService.validateDailyChallengeStatus(dailyChallenge.getStatus(), List.of(Status.NONE));
        dailyChallenge.changeStatus(Status.FAILURE);

        return new UsePointResponse(
                ChallengeConstants.USAGE_POINT,
                user.decreasePoint(ChallengeConstants.USAGE_POINT)
        );
    }

    @Transactional
    public UsePointResponse usePointAndTodayDailyChallengeFailed(Long userId, String timeZone) {
        LocalDate challengeDate = LocalDate.now(ZoneId.of(timeZone));
        DailyChallenge dailyChallenge =
                dailyChallengeService.findDailyChallengeByChallengeDateAndUserIdOrElseThrow(challengeDate, userId);
        User user = userService.findByIdOrThrowException(userId);

        dailyChallengeService.validateDailyChallengeStatus(dailyChallenge.getStatus(), List.of(Status.NONE));
        dailyChallenge.changeStatus(Status.FAILURE);

        return new UsePointResponse(
                ChallengeConstants.USAGE_POINT,
                user.decreasePoint(ChallengeConstants.USAGE_POINT)
        );
    }

    @Transactional
    public EarnPointResponse earnPointAndChallengeEarned(Long userId, LocalDate challengeDate) {
        DailyChallenge dailyChallenge =
                dailyChallengeService.findDailyChallengeByChallengeDateAndUserIdOrElseThrow(challengeDate, userId);
        User user = userService.findByIdOrThrowException(userId);

        dailyChallengeService.validateDailyChallengeStatus(dailyChallenge.getStatus(), List.of(Status.UNEARNED));
        dailyChallenge.changeStatus(Status.EARNED);

        return new EarnPointResponse(user.increasePoint(ChallengeConstants.EARNED_POINT));
    }

    @Transactional(readOnly = true)
    public ChallengePointStatusListResponse getChallengePointStatusList(Long userId) {
        User user = userService.findByIdOrThrowException(userId);
        Challenge challenge = challengeService.findByIdOrElseThrow(user.getCurrentChallengeId());
        List<ChallengePointStatusResponse> challengePointStatusResponseList =
                challenge.getHistoryDailyChallenges().stream()
                .map(dailyChallenge -> new ChallengePointStatusResponse(
                        dailyChallenge.getChallengeDate(),
                        dailyChallenge.getStatus())).toList();

        return new ChallengePointStatusListResponse(
                user.getPoint(),
                challenge.getPeriod(),
                challengePointStatusResponseList
        );
    }
}
