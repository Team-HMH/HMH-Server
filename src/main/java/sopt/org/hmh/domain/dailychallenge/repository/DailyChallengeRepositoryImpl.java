package sopt.org.hmh.domain.dailychallenge.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;

@Repository
@RequiredArgsConstructor
public class DailyChallengeRepositoryImpl implements DailyChallengeRepository {

    private final DailyChallengeJpaRepository dailyChallengeJpaRepository;

    @Override
    public Optional<DailyChallenge> findByChallengeDateAndUserId(LocalDate challengeDate, Long userId) {
        return dailyChallengeJpaRepository.findByChallengeDateAndUserId(challengeDate, userId);
    }

    @Override
    public List<DailyChallenge> findAllByChallengeId(Long challengeId) {
        return dailyChallengeJpaRepository.findAllByChallengeId(challengeId);
    }
}
