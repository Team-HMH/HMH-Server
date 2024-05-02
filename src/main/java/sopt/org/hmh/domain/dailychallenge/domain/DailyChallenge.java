package sopt.org.hmh.domain.dailychallenge.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.app.domain.AppWithUsageGoalTime;
import sopt.org.hmh.global.common.domain.BaseTimeEntity;
import sopt.org.hmh.domain.challenge.domain.Challenge;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class DailyChallenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private Long goalTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "dailyChallenge", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<AppWithUsageGoalTime> apps = new ArrayList<>();

    private LocalDate challengeDate;

    private Long userId;

    @Builder
    public DailyChallenge(Challenge challenge, Long goalTime, Status status) {
        this.challenge = challenge;
        this.goalTime = goalTime;
        this.status = status;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }
}