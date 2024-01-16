package sopt.org.HMH.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.dto.request.AppUsageTimeRequest;
import sopt.org.HMH.domain.app.repository.AppRepository;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.domain.Status;
import sopt.org.HMH.domain.dailychallenge.dto.response.DailyChallengeResponse;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;
    private final ChallengeRepository challengeRepository;
    private final AppRepository appRepository;

    @Transactional
    public DailyChallenge addDailyChallenge(Challenge challenge) {
        DailyChallenge dailyChallenge = dailyChallengeRepository.save(DailyChallenge.builder()
                .challenge(challenge)
                .goalTime(challenge.getGoalTime())
                .build());

        return dailyChallenge;
    }

    public DailyChallengeResponse getDailyChallenge(Long userId, String os) {
        DailyChallenge dailyChallenge = getTodayDailyChallengeByUserId(userId);

        return DailyChallengeResponse.of(dailyChallenge, os);
    }

    @Transactional
    public void modifyDailyChallengeStatusFailure(Long userId) {
        DailyChallenge dailyChallenge = getTodayDailyChallengeByUserId(userId);
        dailyChallenge.setStatus(Status.FAILURE);
    }

    @Transactional
    public void modifyDailyChallengeStatus(Long userId, List<AppUsageTimeRequest> requests, String os) {
        DailyChallenge todayDailyChallenge = getTodayDailyChallengeByUserId(userId);
        long successCount = requests.stream()
                .map(request -> {
                    App app = appRepository.findByDailyChallengeIdAndAppCodeAndOs(
                            todayDailyChallenge.getId(), request.appCode(), os);
                    app.setUsageTime(request.usageTime());
                    return request.usageTime() <= app.getGoalTime();
                })
                .filter(Boolean::booleanValue)
                .count();
        Status status = (successCount == requests.size()) ? Status.UNEARNED : Status.FAILURE;
        todayDailyChallenge.setStatus(status);
    }

    public DailyChallenge getTodayDailyChallengeByUserId(Long userId) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
        val startDateOfChallenge = challenge.getCreatedAt().toLocalDate();
        val todayDailyChallengeIndex = (int) ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(), startDateOfChallenge);

        return challenge.getDailyChallenges().get(todayDailyChallengeIndex);
    }
}