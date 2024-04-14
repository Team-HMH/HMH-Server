package sopt.org.hmh.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.AppConstants;
import sopt.org.hmh.domain.app.domain.AppWithGoalTime;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.AppRemoveRequest;
import sopt.org.hmh.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.hmh.domain.app.dto.response.AppGoalTimeResponse;
import sopt.org.hmh.domain.app.repository.AppWithGoalTimeRepository;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.challenge.domain.ChallengeDay;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.PointUsageResponse;
import sopt.org.hmh.domain.challenge.repository.ChallengeRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final AppWithGoalTimeRepository appWithGoalTimeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Challenge addChallenge(Long userId, Integer period, Long goalTime, String os) {
        validateChallengePeriod(period);
        validateChallengeGoalTime(goalTime);

        Optional<Challenge> previousChallenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);

        Challenge challenge = challengeRepository.save(Challenge.builder()
                .userId(userId)
                .period(period)
                .goalTime(goalTime)
                .build());

        if (previousChallenge.isPresent()) {
            List<AppGoalTimeRequest> previousApps = previousChallenge.get().getApps().stream()
                    .map(app -> new AppGoalTimeRequest(app.getAppCode(), app.getGoalTime()))
                    .toList();
            addApps(challenge, previousApps, os);
        }
        return challenge;
    }

    public ChallengeResponse getChallenge(Long userId) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
        Integer todayIndex = Boolean.TRUE.equals(hasChallengePeriodEnded(challenge.getCreatedAt(), challenge.getPeriod()))
                ? -1
                : challenge.getHistoryDailyChallenges().size();

        return ChallengeResponse.builder()
                .period(challenge.getPeriod())
                .statuses(challenge.getHistoryDailyChallenges()
                        .stream()
                        .map(DailyChallenge::getStatus)
                        .toList())
                .todayIndex(todayIndex)
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(app -> new AppGoalTimeResponse(app.getAppCode(), app.getGoalTime())).toList())
                .build();
    }

    public DailyChallengeResponse getDailyChallenge(Long userId) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);

        return DailyChallengeResponse.builder()
                .status(Boolean.TRUE.equals(challenge.isChallengeFailedToday())
                        ? Status.FAILURE
                        : Status.NONE)
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(app -> new AppGoalTimeResponse(app.getAppCode(), app.getGoalTime())).toList())
                .build();
    }

    @Transactional
    public void removeApp(Challenge challenge, AppRemoveRequest request, String os) {
        validateAppCode(request.appCode());
        AppWithGoalTime appToRemove = appWithGoalTimeRepository
                .findFirstByChallengeIdAndAppCodeAndOsOrElseThrow(challenge.getId(), request.appCode(), os);
        appWithGoalTimeRepository.delete(appToRemove);
    }

    @Transactional
    public void addApps(Challenge challenge, List<AppGoalTimeRequest> requests, String os) {
        List<AppWithGoalTime> appsToUpdate = requests.stream()
                .map(request -> {
                    validateAppExist(challenge.getId(), request.appCode(), os);
                    validateAppCode(request.appCode());
                    validateAppTime(request.goalTime());
                    return AppWithGoalTime.builder()
                            .challenge(challenge)
                            .appCode(request.appCode())
                            .goalTime(request.goalTime())
                            .os(os)
                            .build();
                }).toList();
        appWithGoalTimeRepository.saveAll(appsToUpdate);
    }

    @Transactional
    public void deleteChallengeRelatedByUserId(List<Long> expiredUserIdList) {
        challengeRepository.deleteByUserIdIn(expiredUserIdList);
    }

    @Transactional
    public PointUsageResponse failChallengeByUsagePoint(Long userId) {
        User user = userRepository.findByIdOrThrowException(userId);
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
        challenge.setChallengeFailedToday(true);
        return new PointUsageResponse(
                ChallengeConstants.USAGE_POINT,
                user.decreasePoint(ChallengeConstants.USAGE_POINT)
        );
    }

    private Boolean hasChallengePeriodEnded(LocalDateTime challengeCreatedAt, Integer period) {
        Duration duration = Duration.between(LocalDateTime.now(), challengeCreatedAt);
        long daysDifference = duration.toDays();
        return daysDifference >= period;
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
            throw new ChallengeException(ChallengeError.INVALID_GOAL_TIME_NUMERIC);
        }
    }

    private void validateAppExist(Long challengeId, String appCode, String os) {
        if (appWithGoalTimeRepository.existsByChallengeIdAndAppCodeAndOs(challengeId, appCode, os)) {
            throw new AppException(AppError.APP_EXIST_ALREADY);
        }
    }

    private void validateAppCode(String appCode) {
        if (appCode.isEmpty()) {
            throw new AppException(AppError.INVALID_APP_CODE_NULL);
        }
    }

    private void validateAppTime(Long appTime) {
        if (appTime == null) {
            throw new AppException(AppError.INVALID_TIME_NULL);
        }
        if (appTime > AppConstants.MAXIMUM_APP_TIME || appTime < AppConstants.MINIMUM_APP_TIME)
            throw new AppException(AppError.INVALID_TIME_RANGE);
    }
}
