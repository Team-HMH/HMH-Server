package sopt.org.HMH.domain.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.app.domain.App;

public interface AppRepository extends JpaRepository<App, Long> {

    App findByDailyChallengeIdAndAppCodeAndOs(Long dailyChallengeId, String appCode, String os);
}