package sopt.org.HMH.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
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
    public List<DailyChallenge> addDailyChallengesForPeriod(Challenge challenge, ChallengeRequest request, String os) {
        for (int count = 0; count <= request.period(); count++) {
            DailyChallenge dailyChallenge = dailyChallengeRepository.save(DailyChallenge.builder()
                    .challenge(challenge)
                    .goalTime(request.goalTime())
                    .build());
            appService.addAppByChallengeId(dailyChallenge.getId(), request.apps(), os);
        }

        return challenge.getDailyChallenges();
    }

    public DailyChallengeResponse getDailyChallenge(Long userId, String os) {
        DailyChallenge dailyChallenge = IdConverter.getTodayDailyChallengeByUserId(challengeRepository, userId);

        return DailyChallengeResponse.of(dailyChallenge, os);
    }
}