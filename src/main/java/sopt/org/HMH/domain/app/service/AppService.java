package sopt.org.HMH.domain.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.dto.request.AppDeleteRequest;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.app.repository.AppRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.service.DailyChallengeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;

    private final DailyChallengeService dailyChallengeService;

    @Transactional
    public void removeApp(Long userId, AppDeleteRequest request, String os) {
        App app = appRepository.findByDailyChallengeIdAndAppCodeAndOs(
                dailyChallengeService.getTodayDailyChallengeByUserId(userId).getId(),
                request.appCode(),
                os);

        appRepository.deleteById(app.getId());
    }

    @Transactional
    public void addAppsAndUpdateRemainingDailyChallenge(Long userId, List<AppGoalTimeRequest> requests, String os) {
        dailyChallengeService.getRemainingDailyChallengesByUserId(userId).stream()
                .map((dailyChallenge -> addApps(dailyChallenge, requests, os)));
    }

    @Transactional
    public List<App> addApps(DailyChallenge dailyChallenge, List<AppGoalTimeRequest> requests, String os) {
        List<App> appStream = requests.stream()
                .map(request -> App.builder().dailyChallenge(dailyChallenge)
                        .appCode(request.appCode())
                        .goalTime(request.goalTime())
                        .os(os).build()
                ).toList();

        return appRepository.saveAll(appStream);
    }
}