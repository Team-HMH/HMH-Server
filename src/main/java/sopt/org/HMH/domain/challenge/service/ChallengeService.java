package sopt.org.HMH.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.challenge.dto.response.CreatedChallengeResponse;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dayChallenge.service.DayChallengeService;
import sopt.org.HMH.domain.user.User;
import sopt.org.HMH.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final DayChallengeService dayChallengeService;
    private final UserService userService;

    @Transactional
    public CreatedChallengeResponse addChallenge(Long userId, ChallengeRequest request) {
        User user = userService.getUserById(userId);
        Challenge challenge = challengeRepository.save(Challenge.builder()
                        .period(request.period())
                        .user(user).build());
        dayChallengeService.addDayChallenge(challenge, request.goalTime(), request.apps());

        return CreatedChallengeResponse.of(challenge.getId());
    }
}
