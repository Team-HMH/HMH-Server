package sopt.org.hmh.domain.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.App;
import sopt.org.hmh.domain.app.domain.AppConstants;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.AppDeleteRequest;
import sopt.org.hmh.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.hmh.domain.app.repository.AppRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;

    private final DailyChallengeService dailyChallengeService;

    @Transactional
    public void addAppsAndUpdateRemainingDailyChallenge(Long userId, List<AppGoalTimeRequest> requests, String os) {
        dailyChallengeService.getRemainingDailyChallengesByUserId(userId)
                .forEach(dailyChallenge -> addApps(dailyChallenge, requests, os));
    }

    @Transactional
    public void removeAppAndUpdateRemainingDailyChallenge(Long userId, AppDeleteRequest request, String os) {
        dailyChallengeService.getRemainingDailyChallengesByUserId(userId)
                .forEach(dailyChallenge -> removeApp(dailyChallenge, request, os));
    }

    @Transactional
    public void removeApp(DailyChallenge dailyChallenge, AppDeleteRequest request, String os) {
        validateAppCode(request.appCode());
        appRepository.delete(appRepository.findFirstByDailyChallengeIdAndAppCodeAndOsOrElseThrow(
                dailyChallenge.getId(),
                request.appCode(),
                os));
    }

    @Transactional
    public List<App> addApps(DailyChallenge dailyChallenge, List<AppGoalTimeRequest> requests, String os) {
        List<App> appStream = requests.stream()
                .map(request -> {
                    validateAppExist(dailyChallenge.getId(), request.appCode(), os);
                    validateAppCode(request.appCode());
                    validateAppTime(request.goalTime());
                    return App.builder()
                            .dailyChallenge(dailyChallenge)
                            .appCode(request.appCode())
                            .goalTime(request.goalTime())
                            .os(os)
                            .build();
                }).toList();

        return appRepository.saveAll(appStream);
    }

    private void validateAppExist(Long dailyChallengeId, String appCode, String os) {
        if (appRepository.existsByDailyChallengeIdAndAppCodeAndOs(dailyChallengeId, appCode, os)) {
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