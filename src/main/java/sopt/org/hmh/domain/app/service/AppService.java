package sopt.org.hmh.domain.app.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.AppWithGoalTime;
import sopt.org.hmh.domain.app.domain.AppWithUsageGoalTime;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.AppUsageTimeRequest;
import sopt.org.hmh.domain.app.repository.AppWithUsageGoalTimeRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;

@Service
@RequiredArgsConstructor
@Transactional
public class AppService {

    private final AppWithUsageGoalTimeRepository appWithUsageGoalTimeRepository;

    public void addAppForHistory(List<AppWithGoalTime> currentChallengeApps, List<AppUsageTimeRequest> apps,
            DailyChallenge dailyChallenge, String os) {
        appWithUsageGoalTimeRepository.saveAll(supplementAdditionalInfo(currentChallengeApps, apps, dailyChallenge, os));
    }

    private List<AppWithUsageGoalTime> supplementAdditionalInfo(List<AppWithGoalTime> currentChallengeApps,
            List<AppUsageTimeRequest> apps, DailyChallenge dailyChallenge, String os) {
        return apps.stream().map(app -> AppWithUsageGoalTime.builder()
                .goalTime(this.getGoalTime(currentChallengeApps, app.appCode()))
                .appCode(app.appCode())
                .dailyChallenge(dailyChallenge)
                .os(os)
                .usageTime(app.usageTime())
                .build()
        ).toList();
    }

    private Long getGoalTime(List<AppWithGoalTime> currentChallengeApps, String appCode) {
        return currentChallengeApps.stream()
                .filter(currentChallengeApp -> currentChallengeApp.getAppCode().equals(appCode))
                .findFirst()
                .map(AppWithGoalTime::getGoalTime)
                .orElseThrow(() -> new AppException(AppError.APP_NOT_FOUND));
    }
}
