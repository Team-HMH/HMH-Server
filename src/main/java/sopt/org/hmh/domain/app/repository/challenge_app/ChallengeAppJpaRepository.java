package sopt.org.hmh.domain.app.repository.challenge_app;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.app.domain.ChallengeApp;

public interface ChallengeAppJpaRepository extends JpaRepository<ChallengeApp, Long> {

    Optional<ChallengeApp> findFirstByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os);

    boolean existsByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os);

    List<ChallengeApp> findAllByChallengeId(Long previousChallengeId);
}
