package sopt.org.HMH.domain.dailychallenge.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.global.common.domain.BaseTimeEntity;
import sopt.org.HMH.domain.challenge.domain.Challenge;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class DailyChallenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private Long goalTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "dailyChallenge")
    private final List<App> apps = new ArrayList<>();

    @Builder
    public DailyChallenge(Challenge challenge, Long goalTime) {
        this.challenge = challenge;
        this.goalTime = goalTime;
        this.status = Status.NONE;
    }

    public void modifyDailyChallengeStatusFailure() {
        this.status = Status.FAILURE;
    }
}