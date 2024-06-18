package sopt.org.hmh.domain.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.challenge.domain.Challenge;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeApp extends App {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @NotNull(message = "목표 시간은 null일 수 없습니다.")
    private Long goalTime;

    @Builder
    private ChallengeApp(Challenge challenge, String appCode, Long goalTime, String os) {
        this.challenge = challenge;
        this.os = os;
        this.appCode = appCode;
        this.goalTime = goalTime;
    }
}
