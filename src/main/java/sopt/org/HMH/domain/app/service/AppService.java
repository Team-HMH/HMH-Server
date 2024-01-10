package sopt.org.HMH.domain.app.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.dto.request.AppDeleteRequest;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.app.repository.AppRepository;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppService {

    private final AppRepository appRepository;
    private final DailyChallengeRepository dailyChallengeRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public void addAppByChallengeId(Long dailyChallengeId, List<AppGoalTimeRequest> requests, String os) {
        val dailyChallenge = dailyChallengeRepository.findByIdOrThrowException(dailyChallengeId);

        for (AppGoalTimeRequest request : requests) {
            appRepository.save(App.builder()
                    .dailyChallenge(dailyChallenge)
                    .appCode(request.appCode())
                    .goalTime(request.goalTime())
                    .os(os).build());
        }
    }

    @Transactional
    public void removeApp(Long userId, AppDeleteRequest request) {
        Long latestChallengeId = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId).getId();
        Long latestDayChallengeId = dailyChallengeRepository.findFirstByChallengeIdOrderByCreatedAtDesc(latestChallengeId).getId();
        Long appId = appRepository.findByDailyChallengeIdAndAppCode(latestDayChallengeId, request.appCode()).getId();

        appRepository.deleteById(appId);
    }

    @Transactional
    public void addAppsByUserId(Long userId, List<AppGoalTimeRequest> requests, String os) {
        for (AppGoalTimeRequest request : requests) {
            appRepository.save(App.builder()
                    .dailyChallenge(getTodayChallengeId(userId))
                    .appCode(request.appCode())
                    .goalTime(request.goalTime())
                    .os(os).build());
        }
    }

    private DailyChallenge getTodayChallengeId(final Long userId) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);

        return dailyChallengeRepository.findFirstByChallengeIdOrderByCreatedAtDesc(challenge.getId());
    }
}