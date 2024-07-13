package sopt.org.hmh.domain.challenge.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.challenge.domain.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Optional<Challenge> findById(Long id);

    void deleteByUserIdIn(List<Long> userId);

    void deleteByUserId(Long userId);
}