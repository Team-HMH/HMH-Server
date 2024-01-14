package sopt.org.HMH.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.challenge.dto.response.AddChallengeResponse;
import sopt.org.HMH.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.domain.Status;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.service.DailyChallengeService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final DailyChallengeService dailyChallengeService;

    @Transactional
    public AddChallengeResponse addChallenge(Long userId, ChallengeRequest request, String os) {
        Challenge challenge = challengeRepository.save(Challenge.builder()
                        .period(request.period())
                        .userId(userId).build());
        dailyChallengeService.addDailyChallengesForPeriod(challenge, request, os);
        return AddChallengeResponse.of(challenge.getId());
    }

    public ChallengeResponse getChallenge(Long userId, String os) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);

        return ChallengeResponse.of(challenge);
    }
}
