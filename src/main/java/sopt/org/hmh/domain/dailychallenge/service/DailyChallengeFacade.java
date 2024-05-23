package sopt.org.hmh.domain.dailychallenge.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.service.AppService;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeListRequest;
import sopt.org.hmh.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyChallengeFacade {

    private final DailyChallengeService dailyChallengeService;
    private final AppService appService;
    private final ChallengeService challengeService;
    private final UserService userService;

    public void addFinishedDailyChallengeHistory(Long userId, FinishedDailyChallengeListRequest requests, String os) {
        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge = dailyChallengeService.findByChallengeDateAndUserIdOrThrowException(request.challengeDate(), userId);
            dailyChallengeService.changeStatusByCurrentStatus(dailyChallenge);
            Long currentChallengeId = userService.getCurrentChallengeIdByUserId(userId);
            List<ChallengeApp> currentChallengeApps = challengeService.getCurrentChallengeAppByChallengeId(currentChallengeId);
            appService.addAppForHistory(currentChallengeApps, request.apps(), dailyChallenge, os);
        });
    }
}
