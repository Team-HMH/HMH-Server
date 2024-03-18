package sopt.org.hmh.domain.app.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.dailychallenge.domain.DailyChallenge;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppWithUsageGoalTime extends App {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_challenge_id")
    private DailyChallenge dailyChallenge;

    private Long usageTime;
    private Long goalTime;

    @Builder
    private AppWithUsageGoalTime(DailyChallenge dailyChallenge, String os, String appCode, Long goalTime, Long usageTime) {
        this.dailyChallenge = dailyChallenge;
        this.os = os;
        this.appCode = appCode;
        this.goalTime = goalTime;
        this.usageTime = usageTime;
    }

    // TODO: - 안쓰일듯함
    public void setUsageTime(Long usageTime) {
        this.usageTime = usageTime;
    }
}