package sopt.org.hmh.domain.point.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.point.dto.response.PointUsageResponse;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeError;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeException;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.domain.exception.UserError;
import sopt.org.hmh.domain.user.domain.exception.UserException;
import sopt.org.hmh.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class PointFacade {

    private final UserService userService;
    private final DailyChallengeService dailyChallengeService;

    @Transactional
    public PointUsageResponse failChallengeByUsagePoint(Long userId, LocalDate challengeDate) {
        DailyChallenge dailyChallenge = dailyChallengeService.findByChallengeDateAndUserIdOrThrowException(challengeDate, userId);
        User user = userService.findByIdOrThrowException(userId);

        return usePoint(user, dailyChallenge);
    }

    private PointUsageResponse usePoint(User user, DailyChallenge dailyChallenge) {
        validatePointUsage(user, dailyChallenge);
        dailyChallenge.changeStatus(Status.FAILURE);

        return new PointUsageResponse(
                ChallengeConstants.USAGE_POINT,
                user.decreasePoint(ChallengeConstants.USAGE_POINT)
        );
    }

    private void validatePointUsage(User user, DailyChallenge dailyChallenge) {
        if (user.getPoint() > ChallengeConstants.USAGE_POINT) {
            throw new UserException(UserError.NOT_ENOUGH_POINTS);
        }
        if (dailyChallenge.getStatus() != Status.NONE) {
            throw new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_ALREADY_HANDLED);
        }
    }
}
