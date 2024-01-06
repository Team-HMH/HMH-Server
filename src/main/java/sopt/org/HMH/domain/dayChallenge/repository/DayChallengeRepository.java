package sopt.org.HMH.domain.dayChallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;

public interface DayChallengeRepository extends JpaRepository<DayChallenge, Long> {
}
