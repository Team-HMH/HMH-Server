package sopt.org.HMH.domain.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.challenge.domain.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Challenge findFirstByUserIdOrderByCreatedAtDesc(Long userId);
}
