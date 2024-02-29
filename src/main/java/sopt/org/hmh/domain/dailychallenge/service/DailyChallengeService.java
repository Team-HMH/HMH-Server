package sopt.org.hmh.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.App;
import sopt.org.hmh.domain.app.domain.AppConstants;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.AppUsageTimeRequest;
import sopt.org.hmh.domain.app.repository.AppRepository;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.repository.ChallengeRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeError;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeException;
import sopt.org.hmh.domain.dailychallenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.domain.dailychallenge.repository.DailyChallengeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;
    private final ChallengeRepository challengeRepository;
    private final AppRepository appRepository;

    @Transactional
    public DailyChallenge addDailyChallenge(Challenge challenge) {
        return dailyChallengeRepository.save(DailyChallenge.builder()
                .challenge(challenge)
                .goalTime(challenge.getGoalTime())
                .build());
    }

    public DailyChallengeResponse getDailyChallenge(Long userId, String os) {
        return DailyChallengeResponse.of(getTodayDailyChallengeByUserId(userId), os);
    }

    @Transactional
    public void modifyDailyChallengeStatusFailure(Long userId) {
        DailyChallenge dailyChallenge = getTodayDailyChallengeByUserId(userId);
        dailyChallenge.setStatus(Status.FAILURE);
    }

    @Transactional
    public void modifyDailyChallengeStatus(Long userId, List<AppUsageTimeRequest> requests, String os) {
        DailyChallenge yesterdayDailyChallenge = getYesterdayDailyChallengeByUserId(userId);
        long successCount = requests.stream()
                .filter(request -> {
                    validateModifyDailyChallenge(request.appCode(), request.usageTime());
                    App app = appRepository.findFirstByDailyChallengeIdAndAppCodeAndOsOrElseThrow(
                            yesterdayDailyChallenge.getId(), request.appCode(), os);
                    app.setUsageTime(request.usageTime());
                    return (request.usageTime() <= app.getGoalTime());
                }).count();
        Status status = (successCount == requests.size()) ? Status.UNEARNED : Status.FAILURE;
        yesterdayDailyChallenge.setStatus(status);
    }

    private void validateModifyDailyChallenge(String appCode, Long usageTime) {
        if (appCode.isEmpty()) {
            throw new AppException(AppError.INVALID_APP_CODE_NULL);
        }
        if (usageTime == null) {
            throw new AppException(AppError.INVALID_TIME_NULL);
        }
        if (usageTime > AppConstants.MAXIMUM_APP_TIME || usageTime < AppConstants.MINIMUM_APP_TIME)
            throw new AppException(AppError.INVALID_TIME_RANGE);
    }

    public DailyChallenge getTodayDailyChallengeByUserId(Long userId) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);

        return challenge.getDailyChallenges().get(calculateDaysSinceToday(challenge.getCreatedAt()));
    }

    public DailyChallenge getYesterdayDailyChallengeByUserId(Long userId) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
        validateYesterdayIndex(calculateDaysSinceToday(challenge.getCreatedAt()) - 1);

        return challenge.getDailyChallenges().get(calculateDaysSinceToday(challenge.getCreatedAt()) - 1);
    }

    public List<DailyChallenge> getRemainingDailyChallengesByUserId(Long userId) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);

        return challenge.getDailyChallenges()
                .subList(calculateDaysSinceToday(challenge.getCreatedAt()), challenge.getDailyChallenges().size());
    }

    private Integer calculateDaysSinceToday(LocalDateTime dateToCompare) {
        return (int) ChronoUnit.DAYS.between(dateToCompare.toLocalDate(), LocalDateTime.now().toLocalDate());
    }

    private void validateYesterdayIndex(int index) {
        if (index < 0) {
            throw new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_YESTERDAY_NOT_FOUND);
        }
    }
}