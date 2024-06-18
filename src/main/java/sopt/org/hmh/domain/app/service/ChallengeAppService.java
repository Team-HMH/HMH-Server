package sopt.org.hmh.domain.app.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppRequest;
import sopt.org.hmh.domain.app.repository.ChallengeAppRepository;
import sopt.org.hmh.domain.challenge.domain.Challenge;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeAppService {

    private final ChallengeAppRepository challengeAppRepository;

    public void removeApp(Challenge challenge, String appcode, String os) {
        ChallengeApp appToRemove =
                challengeAppRepository.findFirstByChallengeIdAndAppCodeAndOsOrElseThrow(challenge.getId(), appcode, os);
        challengeAppRepository.delete(appToRemove);
    }

    public void addApps(Challenge challenge, List<ChallengeAppRequest> requests, String os) {
        List<ChallengeApp> appsToUpdate = requests.stream()
                .map(request -> {
                    validateAppExist(challenge.getId(), request.appCode(), os);
                    return ChallengeApp.builder()
                            .challenge(challenge)
                            .appCode(request.appCode())
                            .goalTime(request.goalTime())
                            .os(os)
                            .build();
                }).toList();
        challengeAppRepository.saveAll(appsToUpdate);
    }

    private void validateAppExist(Long challengeId, String appCode, String os) {
        if (challengeAppRepository.existsByChallengeIdAndAppCodeAndOs(challengeId, appCode, os)) {
            throw new AppException(AppError.APP_EXIST_ALREADY);
        }
    }

}
