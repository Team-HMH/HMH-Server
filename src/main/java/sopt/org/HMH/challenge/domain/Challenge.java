package sopt.org.HMH.challenge.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.common.domain.BaseTimeEntity;
import sopt.org.HMH.dayChallenge.domain.DayChallenge;
import sopt.org.HMH.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Challenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer period;
    private Long goalTime;

    @OneToMany
    private List<DayChallenge> dayChallenges;

    @Builder
    public Challenge(User user, Integer period, Long goalTime, DayChallenge dayChallenges) {
        this.period = period;
        this.goalTime = goalTime;
        this.user = user;
        this.dayChallenges.add(dayChallenges);
        this.createdAt = LocalDateTime.now();
    }
}
