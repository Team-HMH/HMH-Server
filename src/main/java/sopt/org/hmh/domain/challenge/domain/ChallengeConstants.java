package sopt.org.hmh.domain.challenge.domain;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ChallengeConstants {
    public static final Long MINIMUM_GOAL_TIME = 3_600_000L; // 1시간
    public static final Long MAXIMUM_GOAL_TIME = 21_600_000L; // 6시간
    public static final Integer USAGE_POINT = 100;
    public static final Integer EARNED_POINT = 20;
    public static final List<Integer> AVAILABLE_CHALLENGE_PERIODS = List.of(7, 14, 20, 30);
}
