package sopt.org.hmh.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppRequest;
import sopt.org.hmh.domain.app.dto.response.ChallengeAppResponse;
import sopt.org.hmh.domain.app.service.ChallengeAppService;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeFacade {

    private final ChallengeService challengeService;
    private final DailyChallengeService dailyChallengeService;
    private final UserService userService;
    private final ChallengeAppService challengeAppService;

    @Transactional
    public Challenge startNewChallengeByPreviousChallenge(Long userId, ChallengeRequest challengeRequest,
            String os) {
        User user = userService.findByIdOrThrowException(userId);
        Long previousChallengeId = userService.getCurrentChallengeIdByUser(user);

        Challenge newChallenge =
                challengeService.addChallengeAndUpdateUserCurrentChallenge(challengeRequest.toEntity(userId), user);

        LocalDate startDate = newChallenge.getCreatedAt().toLocalDate(); // TODO: startDate CreatedAt에서 가져오지 않고 새로 만들기
        dailyChallengeService.addDailyChallenge(userId, startDate, newChallenge);

        challengeAppService.addAppsByPreviousChallengeApp(os, previousChallengeId, newChallenge);

        return newChallenge;
    }

    @Transactional
    public Challenge startFirstChallenge(User user, ChallengeRequest challengeRequest, String os) {
        Long userId = user.getId();

        Challenge challenge = challengeService.addChallengeAndUpdateUserCurrentChallenge(challengeRequest.toEntity(userId), user);

        LocalDate startDate = challenge.getCreatedAt().toLocalDate();
        dailyChallengeService.addDailyChallenge(userId, startDate, challenge);

        return challenge;
    }

    @Transactional(readOnly = true)
    public ChallengeResponse getCurrentChallengeInfo(Long userId) {
        Challenge challenge = this.findCurrentChallengeByUserId(userId);

        return ChallengeResponse.builder()
                .period(challenge.getPeriod())
                .statuses(challenge.getHistoryDailyChallenges()
                        .stream()
                        .map(DailyChallenge::getStatus)
                        .toList())
                .todayIndex(this.calculateTodayIndex(challenge.getCreatedAt(), challenge.getPeriod()))
                .startDate(challenge.getCreatedAt().toLocalDate().toString())
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(app -> new ChallengeAppResponse(app.getAppCode(), app.getGoalTime())).toList())
                .build();
    }

    @Transactional(readOnly = true)
    public DailyChallengeResponse getDailyChallengeInfo(Long userId) {
        Challenge challenge = this.findCurrentChallengeByUserId(userId);

        return DailyChallengeResponse.builder()
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(app -> new ChallengeAppResponse(app.getAppCode(), app.getGoalTime())).toList())
                .build();
    }

    public Challenge findCurrentChallengeByUserId(Long userId) {
        User user = userService.findByIdOrThrowException(userId);
        return challengeService.findByIdOrElseThrow(user.getCurrentChallengeId());
    }

    private Integer calculateTodayIndex(LocalDateTime challengeCreateAt, int period) {
        int daysBetween = (int) ChronoUnit.DAYS.between(challengeCreateAt.toLocalDate(), LocalDate.now());
        return (daysBetween >= period) ? -1 : daysBetween;
    }

    @Transactional
    public void addAppsToCurrentChallenge(Long userId, List<ChallengeAppRequest> requests, String os) {
        Challenge challenge = this.findCurrentChallengeByUserId(userId);
        challengeAppService.addApps(challenge, requests, os);
    }

    @Transactional
    public void removeAppFromCurrentChallenge(Long userId, String appCode, String os) {
        Challenge challenge = this.findCurrentChallengeByUserId(userId);
        challengeAppService.removeApp(challenge, appCode, os);
    }
}