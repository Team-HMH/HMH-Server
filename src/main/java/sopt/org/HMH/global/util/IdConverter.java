package sopt.org.HMH.global.util;

import lombok.val;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.isNull;

public class IdConverter {

    public static Long getUserId(Principal principal) {
        if (isNull(principal)) {
            throw new JwtException(JwtError.EMPTY_PRINCIPLE_EXCEPTION);
        }
        return Long.valueOf(principal.getName());
    }

    public static DailyChallenge getTodayDailyChallengeByUserId(ChallengeRepository challengeRepository,
                                                        DailyChallengeRepository dailyChallengeRepository,
                                                        final Long userId) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
        val startDateOfChallenge = challenge.getDailyChallenges().get(0).getCreatedAt().toLocalDate();

        val todayDailyChallengeIndex = (int) ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(), startDateOfChallenge);
        return challenge.getDailyChallenges().get(todayDailyChallengeIndex);
    }
}