package sopt.org.HMH.domain.challenge.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.global.common.domain.BaseTimeEntity;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long userId;
    private Integer period;
    private Long goalTime;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyChallenge> dailyChallenges  = new ArrayList<>();

    @Builder
    private Challenge(Integer period, Long userId, Long goalTime) {
        this.period = period;
        this.userId = userId;
        this.goalTime = goalTime;
    }
}