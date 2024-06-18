package sopt.org.hmh.domain.challenge.service;

import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.domain.exception.AppError;
import sopt.org.hmh.domain.app.domain.exception.AppException;
import sopt.org.hmh.domain.app.dto.request.AppRemoveRequest;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppRequest;
import sopt.org.hmh.domain.app.dto.response.ChallengeAppResponse;
import sopt.org.hmh.domain.app.repository.ChallengeAppRepository;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.domain.challenge.repository.ChallengeRepository;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.repository.DailyChallengeRepository;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.domain.user.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeAppRepository challengeAppRepository;
    private final DailyChallengeRepository dailyChallengeRepository;
    private final UserService userService;

    @Transactional
    public Challenge addChallenge(Long userId, ChallengeRequest challengeRequest, String os) {
        Challenge challenge = challengeRepository.save(challengeRequest.toEntity(userId));
        LocalDate startDate = challenge.getCreatedAt().toLocalDate();

        User user = userService.findByIdOrThrowException(userId);
        Long previousChallengeId = user.getCurrentChallengeId();
        if (previousChallengeId != null) {
            Challenge previousChallenge = findByIdOrElseThrow(previousChallengeId);
            List<ChallengeAppRequest> previousApps = previousChallenge.getApps().stream()
                    .map(app -> new ChallengeAppRequest(app.getAppCode(), app.getGoalTime()))
                    .toList();
            addApps(challenge, previousApps, os);
        }

        Integer period = challengeRequest.period();
        Long goalTime = challengeRequest.goalTime();
        dailyChallengeRepository.saveAll(IntStream.range(0, period)
                .mapToObj(i -> DailyChallenge.builder()
                        .challengeDate(startDate.plusDays(i))
                        .challenge(challenge)
                        .userId(userId)
                        .goalTime(goalTime).build())
                .toList());

        user.changeCurrentChallengeId(challenge.getId());

        return challenge;
    }

    public ChallengeResponse getChallenge(Long userId) {
        Challenge challenge = findCurrentChallengeByUserId(userId);
        Integer todayIndex = calculateTodayIndex(challenge.getCreatedAt(), challenge.getPeriod());

        return ChallengeResponse.builder()
                .period(challenge.getPeriod())
                .statuses(challenge.getHistoryDailyChallenges()
                        .stream()
                        .map(DailyChallenge::getStatus)
                        .toList())
                .todayIndex(todayIndex)
                .startDate(challenge.getCreatedAt().toLocalDate().toString())
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(app -> new ChallengeAppResponse(app.getAppCode(), app.getGoalTime())).toList())
                .build();
    }

    public DailyChallengeResponse getDailyChallenge(Long userId) {
        Challenge challenge = findCurrentChallengeByUserId(userId);

        return DailyChallengeResponse.builder()
                .goalTime(challenge.getGoalTime())
                .apps(challenge.getApps().stream()
                        .map(app -> new ChallengeAppResponse(app.getAppCode(), app.getGoalTime())).toList())
                .build();
    }

    @Transactional
    public void removeApp(Challenge challenge, AppRemoveRequest request, String os) {
        ChallengeApp appToRemove = challengeAppRepository
                .findFirstByChallengeIdAndAppCodeAndOsOrElseThrow(challenge.getId(), request.appCode(), os);
        challengeAppRepository.delete(appToRemove);
    }

    @Transactional
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

    @Transactional
    public void deleteChallengeRelatedByUserId(List<Long> expiredUserIdList) {
        challengeRepository.deleteByUserIdIn(expiredUserIdList);
    }

    private Integer calculateTodayIndex(LocalDateTime challengeCreateAt, int period) {
        int daysBetween = (int) ChronoUnit.DAYS.between(challengeCreateAt.toLocalDate(), LocalDate.now());
        return (daysBetween >= period) ? -1 : daysBetween;
    }

    private void validateAppExist(Long challengeId, String appCode, String os) {
        if (challengeAppRepository.existsByChallengeIdAndAppCodeAndOs(challengeId, appCode, os)) {
            throw new AppException(AppError.APP_EXIST_ALREADY);
        }
    }

    public Challenge findByIdOrElseThrow(Long challengeId) {
        return challengeRepository.findById(challengeId).orElseThrow(
                () -> new ChallengeException(ChallengeError.CHALLENGE_NOT_FOUND));
    }

    public Challenge findCurrentChallengeByUserId(Long userId) {
        User user = userService.findByIdOrThrowException(userId);
        return findByIdOrElseThrow(user.getCurrentChallengeId());
    }

    public List<ChallengeApp> getCurrentChallengeAppByChallengeId(Long challengeId) {
        return this.findByIdOrElseThrow(challengeId).getApps();
    }
}