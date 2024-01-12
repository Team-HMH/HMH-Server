package sopt.org.HMH.domain.dayChallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;

@Service
@RequiredArgsConstructor
public class DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;

    public Long addDayChallenge(Challenge challenge, Long goalTime) {
        DailyChallenge dailyChallenge = dailyChallengeRepository.save(new DailyChallenge(challenge, goalTime));

        return dailyChallenge.getId();
    }

    public void ChangeDayChallengeStatusFailure(Long userId) {
        // userID를 전달해서 DayChallenge를 찾는 로직
        //getCurrentChallengeByUserId(userId);
        //DayChallenge dayChallenge; // 이건 삭제
        //dayChallenge.changeStatusFailure();
    }
}