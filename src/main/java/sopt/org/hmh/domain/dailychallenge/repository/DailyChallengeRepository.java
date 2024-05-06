package sopt.org.hmh.domain.dailychallenge.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;

public interface DailyChallengeRepository extends JpaRepository<DailyChallenge, Long> {
    Optional<DailyChallenge> findByChallengeDateAndUserId(LocalDate challengeDate, Long userId);
}