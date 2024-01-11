package sopt.org.HMH.domain.dailychallenge.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeException;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.domain.exception.DailyChallengeError;
import sopt.org.HMH.domain.dailychallenge.domain.exception.DailyChallengeException;
import sopt.org.HMH.domain.user.domain.exception.UserError;

public interface DailyChallengeRepository extends JpaRepository<DailyChallenge, Long> {

    DailyChallenge findFirstByChallengeIdOrderByCreatedAtDesc(Long ChallengeId);

    default DailyChallenge findByIdOrThrowException(Long dailyChallengeId) {
        return findById(dailyChallengeId).orElseThrow(()
        -> new DailyChallengeException(DailyChallengeError.CHALLENGE_NOT_FOUND));
    }
}