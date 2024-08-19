package sopt.org.hmh.domain.dailychallenge.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeError;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeException;
import sopt.org.hmh.domain.dailychallenge.repository.DailyChallengeRepository;

@Service
@RequiredArgsConstructor
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;

    public DailyChallenge findDailyChallengeByChallengeDateAndUserIdOrElseThrow(LocalDate challengeDate, Long userId) {
        return dailyChallengeRepository.findByChallengeDateAndUserId(challengeDate, userId)
                .orElseThrow(() -> new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_NOT_FOUND));
    }

    public DailyChallenge findDailyChallengeByChallengePeriodIndex(Challenge challenge, Integer challengePeriodIndex) {
        return Optional.ofNullable(
                challenge.getHistoryDailyChallenges().get(challengePeriodIndex)
        ).orElseThrow(() -> new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_PERIOD_INDEX_NOT_FOUND));
    }

    public DailyChallenge findDailyChallengeByChallengeAndChallengeDate(Challenge challenge, LocalDate challengeDate) {
        return challenge.getHistoryDailyChallenges().stream()
                .filter(dailyChallenge -> dailyChallenge.getChallengeDate().equals(challengeDate))
                .findFirst()
                .orElseThrow(() -> new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_NOT_FOUND));
    }

    public void validateDailyChallengeStatus(Status dailyChallengeStatus, List<Status> expectedStatuses) {
        if (!expectedStatuses.contains(dailyChallengeStatus)) {
            throw new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_ALREADY_PROCESSED);
        }
    }

    public void changeStatusByCurrentStatus(DailyChallenge dailyChallenge) {
        if (dailyChallenge.getStatus() == Status.NONE) {
            dailyChallenge.changeStatus(Status.UNEARNED);
            return;
        }
        this.handleAlreadyProcessedDailyChallenge(dailyChallenge);
    }

    private void handleAlreadyProcessedDailyChallenge(DailyChallenge dailyChallenge) {
        if (dailyChallenge.getStatus() == Status.FAILURE) {
            return;
        }
        throw new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_ALREADY_PROCESSED);
    }

    public void addDailyChallenge(Challenge challenge) {
        validateDuplicateDailyChallenge(challenge);
        dailyChallengeRepository.saveAll(createDailyChallengeByChallengePeriod(challenge));
    }

    private void validateDuplicateDailyChallenge(Challenge challenge) {
        List<LocalDate> localDatesToCheck = IntStream.range(0, challenge.getPeriod())
                .mapToObj(i -> challenge.getStartDate().plusDays(i))
                .toList();

        if (dailyChallengeRepository.existsByUserIdAndChallengeDateIn(challenge.getUserId(), localDatesToCheck)) {
            throw new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_ALREADY_EXISTS);
        }
    }

    public void validatePeriodIndex(Integer periodIndex, Integer todayIndex) {
        if (periodIndex >= todayIndex) throw new DailyChallengeException(DailyChallengeError.PERIOD_INDEX_NOT_VALID);
    }

    private List<DailyChallenge> createDailyChallengeByChallengePeriod(Challenge challenge) {
        LocalDate startDate = challenge.getStartDate();
        Long userId = challenge.getUserId();
        return IntStream.range(0, challenge.getPeriod())
                .mapToObj(i -> DailyChallenge.builder()
                        .challengeDate(startDate.plusDays(i))
                        .challenge(challenge)
                        .userId(userId)
                        .goalTime(challenge.getGoalTime()).build())
                .toList();
    }

    public List<DailyChallenge> getDailyChallengesByChallengeId(Long challengeId) {
        return dailyChallengeRepository.findAllByChallengeId(challengeId);
    }

    public void changeInfoOfDailyChallenges(Long challengeId, List<Status> statuses, LocalDate challengeDate) {
        List<DailyChallenge> dailyChallenges = this.getDailyChallengesByChallengeId(challengeId);
        changeStatusOfDailyChallenges(dailyChallenges, statuses);
        changeChallengeDateOfDailyChallenges(dailyChallenges, challengeDate);
    }

    private void changeStatusOfDailyChallenges(List<DailyChallenge> dailyChallenges, List<Status> statuses) {
        for (int i = 0; i < dailyChallenges.size(); i++) {
            dailyChallenges.get(i).changeStatus(statuses.get(i));
        }
    }

    private void changeChallengeDateOfDailyChallenges(List<DailyChallenge> dailyChallenges, LocalDate challengeDate) {
        for (int i = 0; i < dailyChallenges.size(); i++) {
            dailyChallenges.get(i).changeChallengeDate(challengeDate.plusDays(i));
        }
    }

    public Integer calculateTodayIndex(Challenge challenge, LocalDate now) {
        final int COMPLETED_CHALLENGE_INDEX = -1;
        int daysBetween = (int) ChronoUnit.DAYS.between(challenge.getStartDate(), now);
        return (daysBetween >= challenge.getPeriod()) ? COMPLETED_CHALLENGE_INDEX : daysBetween;
    }
}