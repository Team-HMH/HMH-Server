package sopt.org.hmh.domain.app.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.domain.HistoryApp;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.HistoryAppRequest;
import sopt.org.hmh.domain.app.repository.HistoryAppRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryAppService {

    private final HistoryAppRepository historyAppRepository;

    public void addHistoryApp(
            List<ChallengeApp> currentChallengeApps, List<HistoryAppRequest> apps,
            DailyChallenge dailyChallenge, String os) {
        historyAppRepository.saveAll(supplementAdditionalInfo(currentChallengeApps, apps, dailyChallenge, os));
    }

    private List<HistoryApp> supplementAdditionalInfo(List<ChallengeApp> currentChallengeApps,
                                                      List<HistoryAppRequest> apps, DailyChallenge dailyChallenge, String os) {
        return apps.stream().map(app -> HistoryApp.builder()
                .goalTime(this.getGoalTime(currentChallengeApps, app.appCode()))
                .appCode(app.appCode())
                .dailyChallenge(dailyChallenge)
                .os(os)
                .usageTime(app.usageTime())
                .build()
        ).toList();
    }

    private Long getGoalTime(List<ChallengeApp> currentChallengeApps, String appCode) {
        return currentChallengeApps.stream()
                .filter(currentChallengeApp -> currentChallengeApp.getAppCode().equals(appCode))
                .findFirst()
                .map(ChallengeApp::getGoalTime)
                .orElseThrow(() -> new AppException(AppError.APP_NOT_FOUND));
    }
}
