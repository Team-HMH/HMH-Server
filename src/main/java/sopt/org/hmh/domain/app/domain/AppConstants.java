package sopt.org.hmh.domain.app.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AppConstants {
    public static final Long MINIMUM_APP_TIME = 0L;
    public static final Long MAXIMUM_APP_TIME = 7_200_000L; // 2시간
}