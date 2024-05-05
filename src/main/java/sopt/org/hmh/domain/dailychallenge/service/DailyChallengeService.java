package sopt.org.hmh.domain.dailychallenge.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeError;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeException;
import sopt.org.hmh.domain.dailychallenge.repository.DailyChallengeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;

    public DailyChallenge findByChallengeDateAndUserIdOrThrowException(LocalDate challengeDate, Long userId) {
        return dailyChallengeRepository.findByChallengeDateAndUserId(challengeDate, userId)
                .orElseThrow(() -> new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_NOT_FOUND));
    }

    public void validateDailyChallengeStatus(DailyChallenge dailyChallenge, Status expected) {
        if (dailyChallenge.getStatus() != expected) {
            throw new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_ALREADY_PROCESSED);
        }
    }

    public void finishDailyChallengeByChangeStatus(DailyChallenge dailyChallenge) {
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
}