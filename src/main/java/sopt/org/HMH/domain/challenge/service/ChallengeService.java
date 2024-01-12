package sopt.org.HMH.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.challenge.dto.response.AddChallengeResponse;
import sopt.org.HMH.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.domain.Status;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.domain.user.repository.UserRepository;
import sopt.org.HMH.global.util.IdConverter;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final DailyChallengeService dailyChallengeService;
    private final DailyChallengeRepository dailyChallengeRepository;

    @Transactional
    public AddChallengeResponse addChallenge(Long userId, ChallengeRequest request, String os) {
        Challenge challenge = challengeRepository.save(Challenge.builder()
                        .period(request.period())
                        .userId(userId).build());
        dailyChallengeService.addDailyChallenge(challenge, request.goalTime(), request.apps(), os);

        return AddChallengeResponse.of(challenge.getId());
    }

    public ChallengeResponse getChallange(Long userId, String os) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
        val dailyChallenges = challenge.getDailyChallenges();

        val statuses = new ArrayList<>();
        for (val dailyChallenge : dailyChallenges) {
            statuses.add(dailyChallenge.getStatus());
        }

        val todayIndex = dailyChallengeService.calculateTodayDailyChallengeIndex(challenge.getId());
        return ChallengeResponse.of(challenge, dailyChallenges.get(todayIndex), statuses, todayIndex);
    }
}