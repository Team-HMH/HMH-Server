package sopt.org.hmh.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.AppConstants;
import sopt.org.hmh.domain.app.domain.AppWithUsageGoalTime;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.AppUsageTimeRequest;
import sopt.org.hmh.domain.app.repository.AppWithGoalTimeRepository;
import sopt.org.hmh.domain.app.repository.AppWithUsageGoalTimeRepository;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.repository.ChallengeRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.repository.DailyChallengeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;
    private final ChallengeRepository challengeRepository;
    private final AppWithGoalTimeRepository appWithGoalTimeRepository;
    private final AppWithUsageGoalTimeRepository appWithUsageGoalTimeRepository;

    @Transactional
    public DailyChallenge addHistoryDailyChallenge(Long userId, Status status, List<AppUsageTimeRequest> requests, String os) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
        DailyChallenge dailyChallenge = new DailyChallenge(challenge, challenge.getGoalTime(), status);
        addHistoryApps(challenge, dailyChallenge, requests, os);
        return dailyChallenge;
    }

   private void addHistoryApps(Challenge challenge, DailyChallenge dailyChallenge, List<AppUsageTimeRequest> requests, String os) {
       List<AppWithUsageGoalTime> historyApps = requests.stream()
               .map(request -> {
                   validateAppCode(request.appCode());
                   validateAppTime(request.usageTime());
                   return AppWithUsageGoalTime.builder()
                           .dailyChallenge(dailyChallenge)
                           .usageTime(request.usageTime())
                           .os(os)
                           .appCode(request.appCode())
                           .goalTime(appWithGoalTimeRepository
                                   .findFirstByChallengeIdAndAppCodeAndOsOrElseThrow(challenge.getId(), request.appCode(), os)
                                   .getGoalTime())
                           .build();
               }).toList();
       appWithUsageGoalTimeRepository.saveAll(historyApps);
   }

    // TODO: - 실패 관련 질문한 뒤 자정호출 생각해서 로직 생각하고 수정하기
    @Transactional
    public void modifyDailyChallengeStatusFailure(Long userId) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
//        addHistoryDailyChallenge(challenge, Status.FAILURE, challenge.getApps());
    }

    public Status calculateDailyChallengeStatus(Long userId, List<AppUsageTimeRequest> requests, String os) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
        long successCount = requests.stream()
                .filter(request -> {
                    validateModifyDailyChallenge(request.appCode(), request.usageTime());
                    Long goalTime = appWithGoalTimeRepository.findFirstByChallengeIdAndAppCodeAndOsOrElseThrow(
                            challenge.getId(), request.appCode(), os).getGoalTime();
                    return (request.usageTime() <= goalTime);
                }).count();
        return (successCount == requests.size()) ? Status.UNEARNED : Status.FAILURE;
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
}