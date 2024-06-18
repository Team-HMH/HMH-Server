package sopt.org.hmh.domain.challenge.domain;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ChallengeConstants {
    public static final Integer USAGE_POINT = 100;
    public static final Integer EARNED_POINT = 20;
    public static final List<Integer> AVAILABLE_CHALLENGE_PERIODS = List.of(7, 14, 20, 30);
}
