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
    public void addAppsAndUpdateRemainingDailyChallenge(Long userId, List<AppGoalTimeRequest> requests, String os) {
        dailyChallengeService.getRemainingDailyChallengesByUserId(userId).stream()
                .forEach(dailyChallenge -> addApps(dailyChallenge, requests, os));
    }

    @Transactional
    public void removeAppAndUpdateRemainingDailyChallenge(Long userId, AppDeleteRequest request, String os) {
        dailyChallengeService.getRemainingDailyChallengesByUserId(userId).stream()
                .forEach(dailyChallenge -> removeApp(dailyChallenge, request, os));
    }


    @Transactional
    public void removeApp(DailyChallenge dailyChallenge, AppDeleteRequest request, String os) {
        appRepository.delete(appRepository.findByDailyChallengeIdAndAppCodeAndOs(
                dailyChallenge.getId(),
                request.appCode(),
                os));
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