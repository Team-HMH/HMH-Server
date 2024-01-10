package sopt.org.HMH.domain.app.domain;

import jakarta.persistence.*;
import lombok.*;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_challenge_id")
    private DayChallenge dayChallenge;

    private String os;
    private String appCode;
    private Long useTime;
    private Long goalTime;

    @Builder
    private App(DayChallenge dayChallenge, String appCode, Long goalTime) {
        this.dayChallenge = dayChallenge;
        this.appCode = appCode;
        this.useTime = 0L;
        this.goalTime = goalTime;
    }
}
