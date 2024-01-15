package sopt.org.HMH.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.dto.response.AddChallengeResponse;
import sopt.org.HMH.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.service.DailyChallengeService;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final DailyChallengeService dailyChallengeService;

    @Transactional
    public AddChallengeResponse addChallenge(Long userId, Integer period, Long goalTime) {
        Challenge challenge = challengeRepository.save(Challenge.builder()
                        .period(period)
                        .goalTime(goalTime)
                        .userId(userId).build());
        dailyChallengeService.addDailyChallengesForPeriod(challenge, period, goalTime);
        return AddChallengeResponse.of(challenge.getId());
    }

    public ChallengeResponse getChallenge(Long userId, String os) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
        return ChallengeResponse.of(challenge, os);
    }
}
