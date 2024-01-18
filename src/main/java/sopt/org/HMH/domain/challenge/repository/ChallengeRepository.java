package sopt.org.HMH.domain.challenge.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeError;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeException;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    default Challenge findByUserIdAndCreatedAtBetweenOrThrowException(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return findByUserIdAndCreatedAtBetween(userId, startDate, endDate).orElseThrow(() -> new ChallengeException(
                ChallengeError.CHALLENGE_NOT_FOUND));
    }
    Optional<Challenge> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    Optional<Challenge> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

    default Challenge findFirstByUserIdOrderByCreatedAtDescOrElseThrow(Long userId) {
        return findFirstByUserIdOrderByCreatedAtDesc(userId).orElseThrow(() -> new ChallengeException(
                ChallengeError.CHALLENGE_NOT_FOUND));
    }
}