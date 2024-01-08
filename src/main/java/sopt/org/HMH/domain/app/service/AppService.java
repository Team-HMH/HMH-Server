package sopt.org.HMH.domain.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.app.repository.AppRepository;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeError;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeException;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.domain.dayChallenge.repository.DayChallengeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    private final DayChallengeRepository dayChallengeRepository;

    public List<App> addApp(Long dayChallengeId, List<AppGoalTimeRequest> responses) {
        DayChallenge dayChallenge = dayChallengeRepository.findById(dayChallengeId)
                .orElseThrow(() -> new ChallengeException(ChallengeError.CHALLENGE_NOT_FOUND));
결
        List<App> apps = new ArrayList<>();
        for (AppGoalTimeRequest response: responses) {
            appRepository.save(new App(dayChallenge, response.appCode(), response.goalTime()));
        }

        return apps;
    }
}
