package sopt.org.HMH.global.util;

import lombok.val;
import sopt.org.HMH.domain.challenge.repository.ChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;
import sopt.org.HMH.global.auth.jwt.exception.JwtError;
import sopt.org.HMH.global.auth.jwt.exception.JwtException;

import java.security.Principal;

import static java.util.Objects.isNull;

public class IdConverter {

    public static Long getUserId(Principal principal) {
        if (isNull(principal)) {
            throw new JwtException(JwtError.EMPTY_PRINCIPLE_EXCEPTION);
        }
        return Long.valueOf(principal.getName());
    }

    public static DailyChallenge getTodayDailyChallenge(ChallengeRepository challengeRepository,
                                                        DailyChallengeRepository dailyChallengeRepository,
                                                        final Long userId) {
        val challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);

        return dailyChallengeRepository.findFirstByChallengeIdOrderByCreatedAtDesc(challenge.getId());
    }
}