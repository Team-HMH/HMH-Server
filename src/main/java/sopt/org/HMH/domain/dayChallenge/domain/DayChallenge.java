package sopt.org.HMH.domain.dayChallenge.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.app.domain.App;
import sopt.org.HMH.global.common.domain.BaseTimeEntity;
import sopt.org.HMH.domain.challenge.domain.Challenge;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "day_challenge")
public class DayChallenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private Long goalTime;
    private Boolean isSuccess;
    private Boolean didGettingPoint;

    @OneToMany(mappedBy = "dayChallenge")
    private List<App> apps;

    @Builder
    public DayChallenge(Challenge challenge, Long goalTime) {
        this.challenge = challenge;
        this.goalTime = goalTime;
        this.isSuccess = false;
        this.didGettingPoint = false;
    }
}
