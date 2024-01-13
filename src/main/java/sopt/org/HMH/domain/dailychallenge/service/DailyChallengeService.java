package sopt.org.HMH.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.dto.response.DailyChallengeResponse;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;
import sopt.org.HMH.global.util.IdConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;
    private final AppService appService;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public Long addDailyChallenge(Challenge challenge, Long goalTime, List<AppGoalTimeRequest> apps, String os) {
        DailyChallenge dailyChallenge = dailyChallengeRepository.save(DailyChallenge.builder()
                .challenge(challenge)
                .goalTime(goalTime)
                .build());
        appService.addAppByChallengeId(dailyChallenge.getId(), apps, os);

        return dailyChallenge.getId();
    }

    public DailyChallengeResponse getDailyChallenge(Long userId, String os) {
        DailyChallenge dailyChallenge = IdConverter.getTodayDailyChallengeByUserId(challengeRepository,
                dailyChallengeRepository, userId);

        return DailyChallengeResponse.of(dailyChallenge);
    }
}