package sopt.org.HMH.domain.challenge.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final DailyChallengeService dailyChallengeService;
    private final AppService appService;

    @Transactional
    public Challenge addChallenge(Long userId, Integer period, Long goalTime) {
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
        List<DailyChallenge> lastDailyChallenges = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId).getDailyChallenges();
        if (lastDailyChallenges.size() == 0) { return null; }
        List<AppGoalTimeRequest> lastApps = lastDailyChallenges.get(lastDailyChallenges.size() - 1)
                .getApps()
                .stream()
                .map(app -> new AppGoalTimeRequest(app.getAppCode(), app.getGoalTime()))
                .toList();
        return lastApps;
    }

    public ChallengeResponse getChallenge(Long userId, String os) {
        return ChallengeResponse.of(challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId), os);
    }
}
