package sopt.org.HMH.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dayChallenge.service.DayChallengeService;
import sopt.org.HMH.domain.user.User;
import sopt.org.HMH.domain.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    private final DayChallengeService dayChallengeService;
    private final AppService appService;
    private final UserService userService;

    public Long add(Long userId, ChallengeRequest request, String os) {
        User user = userService.get(userId);
        Challenge challenge = challengeRepository.save(new Challenge(user, request.period()));
        Long dayChallengeId = dayChallengeService.add(challenge, request.goalTime());
        List<App> apps = appService.add(dayChallengeId, request.apps(), os);

        return challenge.getId();
    }
}
