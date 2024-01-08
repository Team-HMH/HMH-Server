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

    public List<App> add(Long dayChallengeId, List<AppGoalTimeRequest> responses, String os) {
        DayChallenge dayChallenge = dayChallengeRepository.findById(dayChallengeId)
                .orElseThrow(() -> new ChallengeException(ChallengeError.CHALLENGE_NOT_FOUND));

        List<App> apps = new ArrayList<>();
        for (AppGoalTimeRequest response: responses) {
            App app = appRepository.save(new App(dayChallenge, response.appCode(), 0L, response.goalTime(), os));
        }

        return apps;
    }
}
