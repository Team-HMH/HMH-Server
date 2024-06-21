package sopt.org.hmh.domain.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryApp extends App {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_challenge_id")
    private DailyChallenge dailyChallenge;

    @NotNull(message = "사용 시간은 null일 수 없습니다.")
    private Long usageTime;

    @NotNull(message = "목표 시간은 null일 수 없습니다.")
    private Long goalTime;

    @Builder
    private HistoryApp(DailyChallenge dailyChallenge, String os, String appCode, Long goalTime, Long usageTime) {
        this.dailyChallenge = dailyChallenge;
        this.os = os;
        this.appCode = appCode;
        this.goalTime = goalTime;
        this.usageTime = usageTime;
    }
}