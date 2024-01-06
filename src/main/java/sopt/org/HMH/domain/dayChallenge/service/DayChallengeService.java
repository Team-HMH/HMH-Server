package sopt.org.HMH.domain.dayChallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.domain.dayChallenge.repository.DayChallengeRepository;

@Service
@RequiredArgsConstructor
public class DayChallengeService {
    private final DayChallengeRepository dayChallengeRepository;

    public Long add(Challenge challenge, Long goalTime) {
        DayChallenge dayChallenge = dayChallengeRepository.save(new DayChallenge(challenge, goalTime));

        return dayChallenge.getId();
    }
}
