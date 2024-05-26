package sopt.org.hmh.domain.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;

import java.util.Optional;

public interface ChallengeAppRepository extends JpaRepository<ChallengeApp, Long> {

    Optional<ChallengeApp> findFirstByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os);

    default ChallengeApp findFirstByChallengeIdAndAppCodeAndOsOrElseThrow(Long challengeId, String appCode, String os) {
        return findFirstByChallengeIdAndAppCodeAndOs(challengeId, appCode, os).orElseThrow(() -> new AppException(
                AppError.APP_NOT_FOUND));
    }

    boolean existsByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os);
}
