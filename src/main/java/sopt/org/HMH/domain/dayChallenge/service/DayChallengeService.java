package sopt.org.HMH.domain.dayChallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.domain.dayChallenge.repository.DayChallengeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DayChallengeService {

    private final DayChallengeRepository dayChallengeRepository;

    private final AppService appService;

    @Transactional
    public Long addDayChallenge(Challenge challenge, Long goalTime, List<AppGoalTimeRequest> apps) {
        DayChallenge dayChallenge = dayChallengeRepository.save(DayChallenge.builder()
                .challenge(challenge)
                .goalTime(goalTime).build());
        appService.addAppByChallengeId(dayChallenge.getId(), apps);

        return dayChallenge.getId();
    }
}
