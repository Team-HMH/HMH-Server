package sopt.org.hmh.domain.challenge.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.global.common.domain.BaseTimeEntity;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull(message = "유저 아이디는 null일 수 없습니다.")
    private Long userId;

    @NotNull(message = "챌린지 기간은 null일 수 없습니다.")
    private Integer period;

    @NotNull(message = "목표 시간은 null일 수 없습니다.")
    private Long goalTime;

    @NotNull(message = "시작 날짜는 null일 수 없습니다.")
    private LocalDate startDate;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChallengeApp> apps;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("challengeDate ASC")
    private List<DailyChallenge> historyDailyChallenges;

    @Builder
    private Challenge(Integer period, Long userId, Long goalTime, List<ChallengeApp> apps, LocalDate startDate) {
        this.period = period;
        this.userId = userId;
        this.goalTime = goalTime;
        this.apps = apps;
        this.startDate = startDate;
    }

    public void changeStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}