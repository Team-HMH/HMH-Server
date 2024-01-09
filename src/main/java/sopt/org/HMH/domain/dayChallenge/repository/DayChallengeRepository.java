package sopt.org.HMH.domain.dayChallenge.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.domain.dayChallenge.domain.exception.DayChallengeError;

public interface DayChallengeRepository extends JpaRepository<DayChallenge, Long> {

    default DayChallenge findByIdOrThrowException(Long dayChallengeId) {
        return findById(dayChallengeId).orElseThrow(() -> new EntityNotFoundException(DayChallengeError.CHALLENGE_NOT_FOUND.getErrorMessage()));
    }
}