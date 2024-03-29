package sopt.org.hmh.domain.dailychallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeError;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeException;

public interface DailyChallengeRepository extends JpaRepository<DailyChallenge, Long> {

}