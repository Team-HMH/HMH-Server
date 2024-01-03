package sopt.org.HMH.common.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class DayChallenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long dayChallengeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private Long goalTime;
    private Boolean isSuccess;
    private Boolean didGettingPoint;

    @OneToMany
    private List<App> apps;

    @Builder
    public DayChallenge(Challenge challenge, Long goalTime, List<App> apps) {
        this.challenge = challenge;
        this.goalTime = goalTime;
        this.isSuccess = false;
        this.didGettingPoint = false;
        this.apps = apps;
        this.createdAt = LocalDateTime.now();
    }
}
