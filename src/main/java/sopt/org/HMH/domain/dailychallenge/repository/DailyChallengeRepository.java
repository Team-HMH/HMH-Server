package sopt.org.HMH.domain.dailychallenge.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.domain.exception.DailyChallengeError;

public interface DailyChallengeRepository extends JpaRepository<DailyChallenge, Long> {

    default DailyChallenge findByIdOrThrowException(Long dailyChallengeId) {
        return findById(dailyChallengeId).orElseThrow(() -> new EntityNotFoundException(DailyChallengeError.CHALLENGE_NOT_FOUND.getErrorMessage()));
    }
}