package sopt.org.HMH.domain.dailychallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dailychallenge.domain.DayChallenge;
import sopt.org.HMH.domain.dailychallenge.repository.DayChallengeRepository;

@Service
@RequiredArgsConstructor
public class DayChallengeService {

    private final DayChallengeRepository dayChallengeRepository;

    public Long addDayChallenge(Challenge challenge, Long goalTime) {
        DayChallenge dayChallenge = dayChallengeRepository.save(new DayChallenge(challenge, goalTime));

        return dayChallenge.getId();
    }
}