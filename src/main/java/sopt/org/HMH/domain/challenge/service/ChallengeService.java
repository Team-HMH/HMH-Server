package sopt.org.HMH.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.service.AppService;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.challenge.dto.response.AddChallengeResponse;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.domain.user.repository.UserRepository;
import sopt.org.HMH.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final DailyChallengeService dailyChallengeService;
    private final AppService appService;
    private final UserService userService;

    @Transactional
    public AddChallengeResponse addChallenge(Long userId,
                                             ChallengeRequest request,
                                             String os) {
        Challenge challenge = challengeRepository.save(Challenge.builder()
                        .period(request.period())
                        .userId(userId).build());
        dailyChallengeService.addDailyChallenge(challenge, request.goalTime(), request.apps(), os);

        return AddChallengeResponse.of(challenge.getId());
    }
}