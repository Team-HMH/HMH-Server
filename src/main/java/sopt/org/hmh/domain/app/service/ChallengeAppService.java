package sopt.org.hmh.domain.app.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppRequest;
import sopt.org.hmh.domain.app.repository.ChallengeAppRepository;
import sopt.org.hmh.domain.challenge.domain.Challenge;

@Service
@RequiredArgsConstructor
public class ChallengeAppService {

    private final ChallengeAppRepository challengeAppRepository;

    public void removeApp(Challenge challenge, String appcode, String os) {
        ChallengeApp appToRemove =
                challengeAppRepository.findFirstByChallengeIdAndAppCodeAndOsOrElseThrow(challenge.getId(), appcode, os);
        challengeAppRepository.delete(appToRemove);
    }

    public void addAppsByPreviousChallengeApp(String os, Long previousChallengeId, Challenge challenge) {
        this.addApps(challengeAppRepository.findAllByChallengeId(previousChallengeId)
                .stream().map(previousApp ->
                        new ChallengeAppRequest(previousApp.getAppCode(), previousApp.getGoalTime())
                                .toEntity(challenge, os))
                .toList()
        );
    }

    public void addApps(List<ChallengeApp> challengeApps) {
        this.validateAppsExist(challengeApps);
        challengeAppRepository.saveAll(challengeApps);
    }

    private void validateAppsExist(List<ChallengeApp> challengeApps) {
        challengeApps.forEach(this::validateAppExist);
    }

    private void validateAppExist(ChallengeApp challengeApp) {
        if (challengeAppRepository.existsByChallengeIdAndAppCodeAndOs(
                challengeApp.getChallenge().getId(), challengeApp.getAppCode(), challengeApp.getOs())) {
            throw new AppException(AppError.APP_EXIST_ALREADY);
        }
    }
}
