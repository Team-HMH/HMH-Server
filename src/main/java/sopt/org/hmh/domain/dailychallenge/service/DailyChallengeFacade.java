package sopt.org.hmh.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.service.AppService;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeListRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyChallengeFacade {

    private final DailyChallengeService dailyChallengeService;
    private final AppService appService;

    public void addFinishedDailyChallengeHistory(Long userId, FinishedDailyChallengeListRequest requests, String os) {
        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge = dailyChallengeService.findByChallengeDateAndUserIdOrThrowException(request.challengeDate(), userId);
            dailyChallengeService.changeStatusByCurrentStatus(dailyChallenge);
            appService.addAppForHistory(request.apps(), dailyChallenge, os);
        });
    }
}
