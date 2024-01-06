package sopt.org.HMH.domain.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.dto.response.AppResponse;
import sopt.org.HMH.domain.app.repository.AppRepository;
import sopt.org.HMH.global.common.exception.GlobalError;
import sopt.org.HMH.global.common.exception.GlobalException;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.domain.dayChallenge.repository.DayChallengeRepository;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppRepository appRepository;
    private final DayChallengeRepository dayChallengeRepository;

    public List<App> add(Long dayChallengeId, List<AppResponse> responses) {
        DayChallenge dayChallenge = dayChallengeRepository.findById(dayChallengeId)
                .orElseThrow(() -> new GlobalException(GlobalError.CHALLENGE_NOT_FOUND));

        List<App> apps = emptyList();
        for (AppResponse response: responses) {
            App app = appRepository.save(new App(dayChallenge, response.appCode(), 0L, response.goalTime(), response.os()));
        }

        return apps;
    }
}
