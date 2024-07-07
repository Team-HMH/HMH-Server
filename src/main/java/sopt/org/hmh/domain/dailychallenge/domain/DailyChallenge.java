package sopt.org.hmh.domain.dailychallenge.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.app.domain.HistoryApp;
import sopt.org.hmh.global.common.domain.BaseTimeEntity;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyChallenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    @NotNull(message = "챌린지는 null일 수 없습니다.")
    private Challenge challenge;

    @OneToMany(mappedBy = "dailyChallenge", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<HistoryApp> apps;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "status값은 null일 수 없습니다.")
    private Status status;

    @NotNull(message = "유저 아이디는 null일 수 없습니다.")
    private Long userId;

    @NotNull(message = "목표 시간은 null일 수 없습니다.")
    private Long goalTime;

    @NotNull(message = "챌린지 날짜는 null일 수 없습니다.")
    private LocalDate challengeDate;

    @Builder
    DailyChallenge(Challenge challenge, Long userId, Long goalTime, LocalDate challengeDate) {
        this.challenge = challenge;
        this.userId = userId;
        this.goalTime = goalTime;
        this.challengeDate = challengeDate;
        this.status = Status.NONE;
    }

    public void changeChallengeDate(LocalDate challengeDate) {
        this.challengeDate = challengeDate;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }
}