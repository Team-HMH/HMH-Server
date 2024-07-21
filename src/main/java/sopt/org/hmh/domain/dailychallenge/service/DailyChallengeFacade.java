package sopt.org.hmh.domain.dailychallenge.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.service.HistoryAppService;
import sopt.org.hmh.domain.challenge.domain.Challenge;
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
    public List<Status> addFinishedDailyChallengeHistory(Long userId, FinishedDailyChallengeListRequest requests, String os) {
        Challenge challenge = challengeService.findByIdOrElseThrow(userService.getCurrentChallengeIdByUserId(userId));

        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge =
                    dailyChallengeService.findDailyChallengeByChallengeIdAndChallengePeriodIndex(
                            challenge.getId(), request.challengePeriodIndex());
            dailyChallengeService.changeStatusByCurrentStatus(dailyChallenge);
            historyAppService.addHistoryApp(challenge.getApps(), request.apps(), dailyChallenge, os);
        });

        return challenge.getHistoryDailyChallenges()
                .stream()
                .map(DailyChallenge::getStatus)
                .toList();
    }

    @Transactional
    public List<Status> changeDailyChallengeStatusByIsSuccess(Long userId, FinishedDailyChallengeStatusListRequest requests) {
        Challenge challenge = challengeService.findByIdOrElseThrow(userService.getCurrentChallengeIdByUserId(userId));

        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge =
                    dailyChallengeService.findDailyChallengeByChallengeIdAndChallengePeriodIndex(
                            challenge.getId(), request.challengePeriodIndex());
            if (request.isSuccess()) {
                dailyChallengeService.validateDailyChallengeStatus(dailyChallenge.getStatus(), List.of(Status.NONE));
                dailyChallenge.changeStatus(Status.UNEARNED);
            } else {
                dailyChallengeService.validateDailyChallengeStatus(
                        dailyChallenge.getStatus(), List.of(Status.NONE, Status.FAILURE));
                dailyChallenge.changeStatus(Status.FAILURE);
            }
        });

        return challenge.getHistoryDailyChallenges()
                .stream()
                .map(DailyChallenge::getStatus)
                .toList();
    }
}
