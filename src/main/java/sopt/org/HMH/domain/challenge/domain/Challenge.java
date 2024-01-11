package sopt.org.HMH.domain.challenge.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.user.domain.User;
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
    @Column(name = "challenge_id")
    private Long id;

    private Long userId;
    private Integer period;

    @OneToMany(mappedBy = "challenge")
    private final List<DailyChallenge> dailyChallenges  = new ArrayList<>();

    @Builder
    private Challenge(Integer period, Long userId) {
        this.period = period;
        this.userId = userId;
    }
}