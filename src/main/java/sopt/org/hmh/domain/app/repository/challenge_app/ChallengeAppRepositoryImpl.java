package sopt.org.hmh.domain.app.repository.challenge_app;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sopt.org.hmh.domain.app.domain.ChallengeApp;

@Repository
@RequiredArgsConstructor
public class ChallengeAppRepositoryImpl implements ChallengeAppRepository{

    private final ChallengeAppJpaRepository challengeAppJpaRepository;

    @Override
    public void saveAll(List<ChallengeApp> challengeApps) {
        challengeAppJpaRepository.saveAll(challengeApps);
    }

    @Override
    public void delete(ChallengeApp appToRemove) {
        challengeAppJpaRepository.delete(appToRemove);
    }

    @Override
    public Optional<ChallengeApp> findFirstByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os) {
        return challengeAppJpaRepository.findFirstByChallengeIdAndAppCodeAndOs(challengeId, appCode, os);
    }

    @Override
    public boolean existsByChallengeIdAndAppCodeAndOs(Long challengeId, String appCode, String os) {
        return challengeAppJpaRepository.existsByChallengeIdAndAppCodeAndOs(challengeId, appCode, os);
    }

    @Override
    public List<ChallengeApp> findAllByChallengeId(Long previousChallengeId) {
        return challengeAppJpaRepository.findAllByChallengeId(previousChallengeId);
    }
}
