package sopt.org.hmh.domain.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.app.domain.App;
import sopt.org.hmh.domain.app.domain.AppWithGoalTime;
import sopt.org.hmh.domain.app.domain.AppWithUsageGoalTime;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;

import java.util.Optional;

public interface AppWithGoalTimeRepository extends JpaRepository<AppWithGoalTime, Long> {

    Optional<AppWithGoalTime> findFirstByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os);

    default AppWithGoalTime findFirstByChallengeIdAndAppCodeAndOsOrElseThrow(Long challengeId, String appCode, String os) {
        return findFirstByChallengeIdAndAppCodeAndOs(challengeId, appCode, os).orElseThrow(() -> new AppException(
                AppError.APP_NOT_FOUND));
    }

    boolean existsByChallengeIdAndAppCodeAndOs(Long ChallengeId, String appCode, String os);
}
