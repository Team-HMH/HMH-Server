package sopt.org.HMH.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.service.DailyChallengeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final DailyChallengeService dailyChallengeService;
    private final AppService appService;

    @Transactional
    public Challenge addChallenge(Long userId, Integer period, Long goalTime) {
        Challenge challenge = challengeRepository.save(Challenge.builder()
                        .period(period)
                        .goalTime(goalTime)
                        .userId(userId).build());

        return challenge;
    }

    @Transactional
    public Challenge addChallengeWithInfo(Long userId, Integer period, Long goalTime, List<AppGoalTimeRequest> apps, String os) {
        Challenge challenge = addChallenge(userId, period, goalTime);
        for (int count = 0; count < challenge.getPeriod(); count++) {
            DailyChallenge dailyChallenge = dailyChallengeService.addDailyChallenge(challenge);
            appService.addApps(dailyChallenge, apps, os);
        }

        return challenge;
    }

    @Transactional
    public Challenge addChallengeWithPastInfo(Long userId, Integer period, Long goalTime, String os) {
        List<AppGoalTimeRequest> lastApps = getLastApps(userId);

        return addChallengeWithInfo(userId, period, goalTime, lastApps, os);
    }

    private List<AppGoalTimeRequest> getLastApps(Long userId) {
        Challenge lastChallenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
        int lastIndexOfDailyChallenge = lastChallenge.getDailyChallenges().size()-1;
        List<AppGoalTimeRequest> lastApps = lastChallenge.getDailyChallenges().get(lastIndexOfDailyChallenge)
                .getApps()
                .stream()
                .map(AppGoalTimeRequest::of)
                .toList();

        return lastApps;
    }

    public ChallengeResponse getChallenge(Long userId, String os) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);

        return ChallengeResponse.of(challenge, os);
    }
}
