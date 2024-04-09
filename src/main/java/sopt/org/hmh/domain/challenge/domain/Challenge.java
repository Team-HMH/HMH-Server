package sopt.org.hmh.domain.challenge.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.app.domain.AppWithGoalTime;
import sopt.org.hmh.global.common.domain.BaseTimeEntity;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long userId;
    private Integer period;
    private Long goalTime;

    private Boolean isChallengeFailedToday;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AppWithGoalTime> apps = new ArrayList<>();

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<DailyChallenge> historyDailyChallenges  = new ArrayList<>();

    @Builder
    private Challenge(Integer period, Long userId, Long goalTime, List<AppWithGoalTime> apps) {
        this.period = period;
        this.userId = userId;
        this.goalTime = goalTime;
        this.isChallengeFailedToday = false;
        this.apps = apps;
    }

    public void setChallengeFailedToday(Boolean challengeFailedToday) {
        isChallengeFailedToday = challengeFailedToday;
    }
}