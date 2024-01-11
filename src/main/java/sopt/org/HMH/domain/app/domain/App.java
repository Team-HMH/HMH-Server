package sopt.org.HMH.domain.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_challenge_id")
    private DailyChallenge dailyChallenge;

    private String os;
    private String appCode;
    private Long usageTime;
    private Long goalTime;

    @Builder
    private App(DailyChallenge dailyChallenge, String appCode, Long goalTime, String os) {
        this.dailyChallenge = dailyChallenge;
        this.appCode = appCode;
        this.usageTime = 0L;
        this.goalTime = goalTime;
        this.os = os;
    }
}