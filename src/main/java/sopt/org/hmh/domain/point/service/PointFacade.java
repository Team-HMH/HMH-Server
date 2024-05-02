package sopt.org.hmh.domain.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.challenge.dto.response.PointUsageResponse;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class PointFacade {

    private final UserService userService;
    private final ChallengeService challengeService;

    @Transactional
    public PointUsageResponse failChallengeByUsagePoint(Long userId) {
        User user = userService.findByIdOrThrowException(userId);
        Challenge challenge = challengeService.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
        challenge.setChallengeFailedToday(true);
        return new PointUsageResponse(
                ChallengeConstants.USAGE_POINT,
                user.decreasePoint(ChallengeConstants.USAGE_POINT)
        );
    }
}
