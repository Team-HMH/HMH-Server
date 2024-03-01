package sopt.org.hmh.domain.app.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.challenge.domain.Challenge;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppWithGoalTime extends App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private Long goalTime;

    @Builder
    private AppWithGoalTime(Challenge challenge, String appCode, Long goalTime, String os) {
        this.challenge = challenge;
        this.os = os;
        this.appCode = appCode;
        this.goalTime = goalTime;
    }
}
