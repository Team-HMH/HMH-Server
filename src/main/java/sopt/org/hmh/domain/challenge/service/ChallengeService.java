package sopt.org.hmh.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.hmh.domain.app.service.AppService;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.challenge.domain.ChallengeDay;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.repository.ChallengeRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final DailyChallengeService dailyChallengeService;
    private final AppService appService;

    @Transactional
    public Challenge addChallenge(Long userId, Integer period, Long goalTime) {
        validateChallengePeriod(period);
        validateChallengeGoalTime(goalTime);
        return challengeRepository.save(Challenge.builder()
                .period(period)
                .goalTime(goalTime)
                .userId(userId).build());
    }

    @Transactional
    public Challenge updateChallengeForPeriodWithInfo(Challenge challenge, List<AppGoalTimeRequest> apps, String os) {
        for (int count = 0; count < challenge.getPeriod(); count++) {
            DailyChallenge dailyChallenge = dailyChallengeService.addDailyChallenge(challenge);
            if (nonNull(apps)) {
                appService.addApps(dailyChallenge, apps, os);
            }
        }

        return challenge;
    }

    public List<AppGoalTimeRequest> getLastApps(Long userId) {
        List<DailyChallenge> lastDailyChallenges = challengeRepository
                .findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId)
                .getDailyChallenges();
        if (lastDailyChallenges.isEmpty()) { return null; }
        return lastDailyChallenges.get(lastDailyChallenges.size() - 1)
                .getApps()
                .stream()
                .map(app -> new AppGoalTimeRequest(app.getAppCode(), app.getGoalTime()))
                .toList();
    }

    public ChallengeResponse getChallenge(Long userId, String os) {
        return ChallengeResponse.of(challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId), os);
    }

    private void validateChallengePeriod(Integer period) {
        if (period == null) {
            throw new ChallengeException(ChallengeError.INVALID_PERIOD_NULL);
        }
        if (period != ChallengeDay.DAYS7.getValue() && period != ChallengeDay.DAYS14.getValue()) {
            throw new ChallengeException(ChallengeError.INVALID_PERIOD_NUMERIC);
        }
    }

    private void validateChallengeGoalTime(Long goalTime) {
        if (goalTime == null) {
            throw new ChallengeException(ChallengeError.INVALID_GOAL_TIME_NULL);
        }
        if (goalTime < ChallengeConstants.MINIMUM_GOAL_TIME || goalTime > ChallengeConstants.MAXIMUM_GOAL_TIME) {
            throw new ChallengeException(ChallengeError.INVALID_GOAL_TIME_NULL);
        }
    }

    @Transactional
    public void deleteChallengeRelatedByUserId(List<Long> expiredUserIdList) {
        challengeRepository.deleteByUserIdIn(expiredUserIdList);
    }
}
