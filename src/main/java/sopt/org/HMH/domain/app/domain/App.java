package sopt.org.HMH.domain.app.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;

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
    @JoinColumn(name = "dayChallenge_id")
    private DayChallenge dayChallenge;

    private String os;
    private String appCode;
    private Long useTime;
    private Long goalTime;

    @Builder
    public App(DayChallenge dayChallenge, String appCode, Long useTime, Long goalTime, String os) {
        this.os = os;
        this.dayChallenge = dayChallenge;
        this.appCode = appCode;
        this.useTime = 0L;
        this.goalTime = goalTime;
    }
}
