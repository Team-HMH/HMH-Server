package sopt.org.HMH.domain.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.app.repository.AppRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    private final DailyChallengeRepository dailyChallengeRepository;

    public List<App> addApp(Long dailyChallengeId, List<AppGoalTimeRequest> requests) {
        DailyChallenge dailyChallenge = dailyChallengeRepository.findByIdOrThrowException(dailyChallengeId);

        List<App> apps = new ArrayList<>();
        for (AppGoalTimeRequest request: requests) {
            appRepository.save(new App(dailyChallenge, request.appCode(), request.goalTime()));
        }

        return apps;
    }
}