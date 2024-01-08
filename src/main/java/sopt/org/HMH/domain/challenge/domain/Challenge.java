package sopt.org.HMH.domain.challenge.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.global.common.domain.BaseTimeEntity;
import sopt.org.HMH.domain.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "challenge")
public class Challenge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer period;

    @OneToMany
    private List<DayChallenge> dayChallenges;

    public Challenge(User user, Integer period) {
        this.user = user;
        this.period = period;
        this.dayChallenges = new ArrayList<>();
    }
}
