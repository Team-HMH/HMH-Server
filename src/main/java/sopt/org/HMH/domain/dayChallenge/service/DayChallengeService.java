package sopt.org.HMH.domain.dayChallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.domain.dayChallenge.repository.DayChallengeRepository;

@Service
@RequiredArgsConstructor
public class DayChallengeService {

    private final DayChallengeRepository dayChallengeRepository;

    public Long addDayChallenge(Challenge challenge, Long goalTime) {
        DayChallenge dayChallenge = dayChallengeRepository.save(new DayChallenge(challenge, goalTime));

        return dayChallenge.getId();
    }

    public void ChangeDayChallengeStatusFailure(Long userId) {
        // userID를 전달해서 DayChallenge를 찾는 로직
        //getCurrentChallengeByUserId(userId);
        //DayChallenge dayChallenge; // 이건 삭제
        //dayChallenge.changeStatusFailure();
    }
}