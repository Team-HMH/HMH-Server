package sopt.org.hmh.domain.challenge.service;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppRequest;
import sopt.org.hmh.domain.app.dto.response.ChallengeAppResponse;
import sopt.org.hmh.domain.app.service.ChallengeAppService;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.dto.request.NewChallengeOrder;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.hmh.domain.user.service.UserService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeFacade {

    private final ChallengeService challengeService;
    private final DailyChallengeService dailyChallengeService;
    private final UserService userService;
    private final ChallengeAppService challengeAppService;

    @Transactional
    public void startNewChallenge(NewChallengeOrder newChallengeOrder) {
        Challenge newChallenge = challengeService.addChallenge(newChallengeOrder.toChallengeEntity());
        userService.changeCurrentChallengeIdByUserId(newChallengeOrder.getUserId(), newChallenge.getId());

        dailyChallengeService.addDailyChallenge(newChallenge);

        this.addAppsByNewChallengeOrder(newChallengeOrder, newChallenge);
    }

    private void addAppsByNewChallengeOrder(NewChallengeOrder newChallengeOrder, Challenge newChallenge) {
        if (newChallengeOrder.isFirstChallenge()) {
            challengeAppService.addApps(newChallengeOrder.toChallengeAppEntities(newChallenge));
            return;
        }
        Long previousChallengeId = userService.getCurrentChallengeIdByUserId(newChallengeOrder.getUserId());
        challengeAppService.addAppsByPreviousChallengeApp(newChallengeOrder.getOs(), previousChallengeId, newChallenge);
    }

    @Transactional(readOnly = true)
    public ChallengeResponse getCurrentChallengeInfo(Long userId, String timeZone) {
        Challenge currentChallenge = this.findCurrentChallengeByUserId(userId);
        return ChallengeResponse.of(currentChallenge, timeZone);
    }

    @Transactional(readOnly = true)
    public DailyChallengeResponse getDailyChallengeInfo(Long userId, String timeZone) {
        Challenge challenge = this.findCurrentChallengeByUserId(userId);
        LocalDate localDateNow = LocalDate.now(ZoneId.of(timeZone));
        DailyChallenge todayChallenge =
                dailyChallengeService.findDailyChallengeByChallengeAndChallengeDate(challenge, localDateNow);

        return DailyChallengeResponse.builder()
                .status(todayChallenge.getStatus())
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(challengeApp -> new ChallengeAppResponse(
                                challengeApp.getAppCode(),
                                challengeApp.getGoalTime()
                        )).toList())
                .build();
    }

    public Challenge findCurrentChallengeByUserId(Long userId) {
        Long currentChallengeId = userService.getCurrentChallengeIdByUserId(userId);
        return challengeService.findByIdOrElseThrow(currentChallengeId);
    }

    @Transactional
    public void addAppsToCurrentChallenge(Long userId, List<ChallengeAppRequest> requests, String os) {
        Challenge challenge = this.findCurrentChallengeByUserId(userId);
        challengeAppService.addApps(
                requests.stream()
                        .map(request -> request.toEntity(challenge, os))
                        .toList()
        );
    }

    @Transactional
    public void removeAppFromCurrentChallenge(Long userId, String appCode, String os) {
        Challenge challenge = this.findCurrentChallengeByUserId(userId);
        challengeAppService.removeApp(challenge, appCode, os);
    }
}