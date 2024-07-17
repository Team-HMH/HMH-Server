package sopt.org.hmh.domain.app.repository.challenge_app;

import java.util.List;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import java.util.Optional;

public interface ChallengeAppRepository {

    void saveAll(List<ChallengeApp> challengeApps);

    void delete(ChallengeApp appToRemove);

    Optional<ChallengeApp> findFirstByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os);

    boolean existsByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os);

    List<ChallengeApp> findAllByChallengeId(Long previousChallengeId);
}
