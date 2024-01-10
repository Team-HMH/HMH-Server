package sopt.org.HMH.domain.app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "app")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_challenge_id")
    private DailyChallenge dailyChallenge;

    private String appCode;
    private Long useTime;
    private Long goalTime;

    public App(DailyChallenge dailyChallenge, String appCode, Long goalTime) {
        this.dailyChallenge = dailyChallenge;
        this.appCode = appCode;
        this.useTime = 0L;
        this.goalTime = goalTime;
    }
}