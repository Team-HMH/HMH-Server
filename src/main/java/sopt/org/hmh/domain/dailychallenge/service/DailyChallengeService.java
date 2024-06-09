package sopt.org.hmh.domain.dailychallenge.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeError;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeException;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeStatusListRequest;
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

    public void validateDailyChallengeStatus(Status dailyChallengeStatus, List<Status> expectedStatuses) {
        expectedStatuses.forEach(expected -> {
            if (dailyChallengeStatus != expected) {
                throw new DailyChallengeException(DailyChallengeError.DAILY_CHALLENGE_ALREADY_PROCESSED);
            }
        });

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

    public void changeDailyChallengeStatusByIsSuccess(Long userId, FinishedDailyChallengeStatusListRequest requests) {
        requests.finishedDailyChallenges().forEach(request -> {
            DailyChallenge dailyChallenge = this.findByChallengeDateAndUserIdOrThrowException(request.challengeDate(), userId);
            if (request.isSuccess()) {
                this.validateDailyChallengeStatus(dailyChallenge.getStatus(), List.of(Status.NONE));
                dailyChallenge.changeStatus(Status.UNEARNED);
            } else {
                this.validateDailyChallengeStatus(dailyChallenge.getStatus(), List.of(Status.NONE, Status.FAILURE));
                dailyChallenge.changeStatus(Status.FAILURE);
            }
        });
    }
}