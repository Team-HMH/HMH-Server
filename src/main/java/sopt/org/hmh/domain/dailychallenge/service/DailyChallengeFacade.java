package sopt.org.hmh.domain.dailychallenge.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<Status> addFinishedDailyChallengeHistory(Long userId, FinishedDailyChallengeListRequest request, String os, String timeZone) {
        Challenge challenge = challengeService.findByIdOrElseThrow(userService.getCurrentChallengeIdByUserId(userId));
        Integer todayIndex = dailyChallengeService.calculateTodayIndex(challenge, LocalDate.now(ZoneId.of(timeZone)));

        request.finishedDailyChallenges().forEach(challengeRequest -> {
            dailyChallengeService.validatePeriodIndex(challengeRequest.challengePeriodIndex(), todayIndex);
            DailyChallenge dailyChallenge = dailyChallengeService
                    .findDailyChallengeByChallengePeriodIndex(challenge, challengeRequest.challengePeriodIndex());
            dailyChallengeService.changeStatusByCurrentStatus(dailyChallenge);
            historyAppService.addHistoryApp(challenge.getApps(), challengeRequest.apps(), dailyChallenge, os);
        });

        return getHistoryDailyChallenges(challenge);
    }

    @Transactional
    public List<Status> changeDailyChallengeStatusByIsSuccess(Long userId, FinishedDailyChallengeStatusListRequest request) {
        Challenge challenge = challengeService.findByIdOrElseThrow(userService.getCurrentChallengeIdByUserId(userId));

        request.finishedDailyChallenges().forEach(challengeRequest -> {
            DailyChallenge dailyChallenge = dailyChallengeService
                    .findDailyChallengeByChallengePeriodIndex(challenge, challengeRequest.challengePeriodIndex());
            if (challengeRequest.isSuccess()) {
                dailyChallengeService.validateDailyChallengeStatus(dailyChallenge.getStatus(), List.of(Status.NONE));
                dailyChallenge.changeStatus(Status.UNEARNED);
            } else {
                dailyChallengeService.validateDailyChallengeStatus(
                        dailyChallenge.getStatus(), List.of(Status.NONE, Status.FAILURE));
                dailyChallenge.changeStatus(Status.FAILURE);
            }
        });

        return getHistoryDailyChallenges(challenge);
    }

    private List<Status> getHistoryDailyChallenges(Challenge challenge) {
        return challenge.getHistoryDailyChallenges()
                .stream()
                .map(DailyChallenge::getStatus)
                .toList();
    }
}
