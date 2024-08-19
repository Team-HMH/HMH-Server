package sopt.org.hmh.domain.dailychallenge.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.QDailyChallenge;

@Repository
@RequiredArgsConstructor
public class DailyChallengeRepositoryImpl implements DailyChallengeRepository {

    private final DailyChallengeJpaRepository dailyChallengeJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public void saveAll(List<DailyChallenge> dailyChallengeByChallengePeriod) {
        dailyChallengeJpaRepository.saveAll(dailyChallengeByChallengePeriod);
    }

    @Override
    public Optional<DailyChallenge> findByChallengeDateAndUserId(LocalDate challengeDate, Long userId) {
        return dailyChallengeJpaRepository.findByChallengeDateAndUserId(challengeDate, userId);
    }

    @Override
    public List<DailyChallenge> findAllByChallengeId(Long challengeId) {
        return dailyChallengeJpaRepository.findAllByChallengeId(challengeId);
    }

    @Override
    public boolean existsByUserIdAndChallengeDateIn(Long userId, List<LocalDate> localDates) {
        QDailyChallenge dailyChallenge = QDailyChallenge.dailyChallenge;
        Integer fetchOne = queryFactory.selectOne()
                .from(dailyChallenge)
                .where(dailyChallenge.userId.eq(userId)
                        .and(dailyChallenge.challengeDate.in(localDates)))
                .fetchFirst();
        return fetchOne != null;
    }
}
