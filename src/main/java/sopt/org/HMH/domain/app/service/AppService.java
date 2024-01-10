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
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.domain.dayChallenge.repository.DayChallengeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppService {

    private final AppRepository appRepository;
    private final DayChallengeRepository dayChallengeRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public void addAppByChallengeId(Long dayChallengeId, List<AppGoalTimeRequest> requests) {
        val dayChallenge = dayChallengeRepository.findByIdOrThrowException(dayChallengeId);

        for (AppGoalTimeRequest request : requests) {
            appRepository.save(App.builder()
                            .dayChallenge(dayChallenge)
                            .appCode(request.appCode())
                            .goalTime(request.goalTime()).build());
        }
    }

    @Transactional
    public void removeApp(Long userId, AppDeleteRequest request) {
        Long latestChallengeId = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId).getId();
        Long latestDayChallengeId = dayChallengeRepository.findFirstByChallengeIdOrderByCreatedAtDesc(latestChallengeId).getId();
        Long appId = appRepository.findByDayChallengeIdAndAppCode(latestDayChallengeId, request.appCode()).getId();

        appRepository.deleteById(appId);
    }

    @Transactional
    public void addAppsByUserId(Long userId, List<AppGoalTimeRequest> requests) {
        for (AppGoalTimeRequest request : requests) {
            appRepository.save(App.builder()
                    .dayChallenge(getTodayChallengeId(userId))
                    .appCode(request.appCode())
                    .goalTime(request.goalTime()).build());
        }
    }

    private DayChallenge getTodayChallengeId(final Long userId) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);

        return dayChallengeRepository.findFirstByChallengeIdOrderByCreatedAtDesc(challenge.getId());
    }
}