package sopt.org.hmh.domain.dailychallenge.domain.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class DailyChallengeException extends ExceptionBase {

    public DailyChallengeException(DailyChallengeError error) {
        super(error);
    }
}