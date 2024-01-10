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
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer period;

    @OneToMany(mappedBy = "challenge")
    private final List<DayChallenge> dayChallenges  = new ArrayList<>();

    @Builder
    private Challenge(User user, Integer period) {
        this.user = user;
        this.period = period;
    }
}