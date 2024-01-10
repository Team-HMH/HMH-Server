package sopt.org.HMH.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;

@Service
@RequiredArgsConstructor
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;

    public Long addDailyChallenge(Challenge challenge, Long goalTime) {
        DailyChallenge dailyChallenge = dailyChallengeRepository.save(new DailyChallenge(challenge, goalTime));

        return dailyChallenge.getId();
    }
}