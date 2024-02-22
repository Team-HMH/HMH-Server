package sopt.org.hmh.domain.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.app.domain.App;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;

import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Long> {

    Optional<App> findFirstByDailyChallengeIdAndAppCodeAndOs(Long dailyChallengeId, String appCode, String os);

    default App findFirstByDailyChallengeIdAndAppCodeAndOsOrElseThrow(Long dailyChallengeId, String appCode, String os) {
        return findFirstByDailyChallengeIdAndAppCodeAndOs(dailyChallengeId, appCode, os).orElseThrow(() -> new AppException(
                AppError.APP_NOT_FOUND));
    }

    boolean existsByDailyChallengeIdAndAppCodeAndOs(Long dailyChallengeId, String appCode, String os);
}