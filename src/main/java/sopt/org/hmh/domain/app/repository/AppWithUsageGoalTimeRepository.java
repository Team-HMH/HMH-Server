package sopt.org.hmh.domain.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.app.domain.AppWithUsageGoalTime;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;

import java.util.Optional;

public interface AppWithUsageGoalTimeRepository extends JpaRepository<AppWithUsageGoalTime, Long> {

    Optional<AppWithUsageGoalTime> findFirstByDailyChallengeIdAndAppCodeAndOs(Long dailyChallengeId, String appCode, String os);

    default AppWithUsageGoalTime findFirstByDailyChallengeIdAndAppCodeAndOsOrElseThrow(Long dailyChallengeId, String appCode, String os) {
        return findFirstByDailyChallengeIdAndAppCodeAndOs(dailyChallengeId, appCode, os).orElseThrow(() -> new AppException(
                AppError.APP_NOT_FOUND));
    }
}
