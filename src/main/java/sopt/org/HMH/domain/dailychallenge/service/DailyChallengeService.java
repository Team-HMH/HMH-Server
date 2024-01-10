package sopt.org.HMH.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;
    private final AppService appService;

    public Long addDailyChallenge(Challenge challenge, Long goalTime, List<AppGoalTimeRequest> apps, String os) {
        DailyChallenge dailyChallenge = dailyChallengeRepository.save(DailyChallenge.builder()
                .challenge(challenge)
                .goalTime(goalTime)
                .build());
        appService.addAppByChallengeId(dailyChallenge.getId(), apps, os);

        return dailyChallenge.getId();
    }
}