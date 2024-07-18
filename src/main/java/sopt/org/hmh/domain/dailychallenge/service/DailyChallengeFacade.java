package sopt.org.hmh.domain.dailychallenge.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.service.HistoryAppService;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeListRequest;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeStatusListRequest;
import sopt.org.hmh.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class DailyChallengeFacade {

    private final DailyChallengeService dailyChallengeService;
    private final HistoryAppService historyAppService;
    private final ChallengeService challengeService;
    private final UserService userService;

    @Transactional
    public void addFinishedDailyChallengeHistory(Long userId, FinishedDailyChallengeListRequest requests, String os) {
        Long currentChallengeId = userService.getCurrentChallengeIdByUserId(userId);
        List<ChallengeApp> currentChallengeApps =
                challengeService.getCurrentChallengeAppByChallengeId(currentChallengeId);

        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge =
                    dailyChallengeService.findDailyChallengeByChallengeIdAndChallengePeriodIndex(
                            currentChallengeId, request.challengePeriodIndex());
            dailyChallengeService.changeStatusByCurrentStatus(dailyChallenge);
            historyAppService.addHistoryApp(currentChallengeApps, request.apps(), dailyChallenge, os);
        });
    }

    @Transactional
    public void changeDailyChallengeStatusByIsSuccess(Long userId, FinishedDailyChallengeStatusListRequest requests) {
        Long currentChallengeId = userService.getCurrentChallengeIdByUserId(userId);
        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge =
                    dailyChallengeService.findDailyChallengeByChallengeIdAndChallengePeriodIndex(
                            currentChallengeId, request.challengePeriodIndex());
            if (request.isSuccess()) {
                dailyChallengeService.validateDailyChallengeStatus(dailyChallenge.getStatus(), List.of(Status.NONE));
                dailyChallenge.changeStatus(Status.UNEARNED);
            } else {
                dailyChallengeService.validateDailyChallengeStatus(
                        dailyChallenge.getStatus(), List.of(Status.NONE, Status.FAILURE));
                dailyChallenge.changeStatus(Status.FAILURE);
            }
        });
    }
}
