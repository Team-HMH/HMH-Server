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
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeListRequestDeprecated;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeStatusListRequestDeprecated;
import sopt.org.hmh.domain.user.service.UserService;

@Service
@Deprecated
@RequiredArgsConstructor
public class DailyChallengeFacadeDeprecated {

    private final DailyChallengeService dailyChallengeService;
    private final HistoryAppService historyAppService;
    private final ChallengeService challengeService;
    private final UserService userService;

    @Transactional
    @Deprecated
    public void addFinishedDailyChallengeHistory(Long userId, FinishedDailyChallengeListRequestDeprecated requests, String os) {
        Long currentChallengeId = userService.getCurrentChallengeIdByUserId(userId);
        List<ChallengeApp> currentChallengeApps =
                challengeService.getCurrentChallengeAppByChallengeId(currentChallengeId);

        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge =
                    dailyChallengeService.findDailyChallengeByChallengeDateAndUserIdOrElseThrow(request.challengeDate(), userId);
            dailyChallengeService.changeStatusByCurrentStatus(dailyChallenge);
            historyAppService.addHistoryApp(currentChallengeApps, request.apps(), dailyChallenge, os);
        });
    }

    @Transactional
    @Deprecated
    public void changeDailyChallengeStatusByIsSuccess(Long userId, FinishedDailyChallengeStatusListRequestDeprecated requests) {
        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge =
                    dailyChallengeService.findDailyChallengeByChallengeDateAndUserIdOrElseThrow(request.challengeDate(), userId);
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
