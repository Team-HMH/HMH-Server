package sopt.org.hmh.domain.dailychallenge.service;

import java.time.LocalDate;
import java.util.List;
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

    public DailyChallenge findByChallengeDateAndUserIdOrThrowException(LocalDate challengeDate, Long userId) {
        return dailyChallengeRepository.findByChallengeDateAndUserId(challengeDate, userId)
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
        dailyChallengeRepository.saveAll(createDailyChallengeByChallengePeriod(challenge));
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
        List<DailyChallenge> dailyChallenges = getDailyChallengesByChallengeId(challengeId);
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
}