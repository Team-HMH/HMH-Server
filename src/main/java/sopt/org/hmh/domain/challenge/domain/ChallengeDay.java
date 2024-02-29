package sopt.org.hmh.domain.challenge.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ChallengeDay {

    DAYS7(7),
    DAYS14(14),
    ;

    private final int value;
}
