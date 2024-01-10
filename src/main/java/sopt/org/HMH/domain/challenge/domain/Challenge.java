package sopt.org.HMH.domain.challenge.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.global.common.domain.BaseTimeEntity;
import sopt.org.HMH.domain.dailychallenge.domain.DailyChallenge;

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

    @OneToMany(mappedBy = "challenge")
    private List<DailyChallenge> dailyChallenges;

    public Challenge(User user, Integer period) {
        this.user = user;
        this.period = period;
        this.dailyChallenges = new ArrayList<>();
    }
}