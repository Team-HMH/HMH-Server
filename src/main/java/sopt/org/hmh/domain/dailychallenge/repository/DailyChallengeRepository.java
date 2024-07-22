package sopt.org.hmh.domain.dailychallenge.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;

public interface DailyChallengeRepository {

    void saveAll(List<DailyChallenge> dailyChallengeByChallengePeriod);

    Optional<DailyChallenge> findByChallengeDateAndUserId(LocalDate challengeDate, Long userId);

    List<DailyChallenge> findAllByChallengeIdOrderByChallengeDate(Long challengeId);

    boolean existsByUserIdAndChallengeDateIn(Long userId, List<LocalDate> localDates);
}