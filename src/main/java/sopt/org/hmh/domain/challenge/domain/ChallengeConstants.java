package sopt.org.hmh.domain.challenge.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ChallengeConstants {
    public static final Long MINIMUM_GOAL_TIME = 7200000L;
    public static final Long MAXIMUM_GOAL_TIME = 21600000L;
    public static final Integer USAGE_POINT = 100;
    public static final Integer EARNED_POINT = 20;

}
