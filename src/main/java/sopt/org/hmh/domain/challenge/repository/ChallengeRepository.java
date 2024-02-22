package sopt.org.hmh.domain.challenge.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Optional<Challenge> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

    void deleteByUserIdIn(List<Long> UserId);

    default Challenge findFirstByUserIdOrderByCreatedAtDescOrElseThrow(Long userId) {
        return findFirstByUserIdOrderByCreatedAtDesc(userId).orElseThrow(() -> new ChallengeException(
                ChallengeError.CHALLENGE_NOT_FOUND));
    }
}