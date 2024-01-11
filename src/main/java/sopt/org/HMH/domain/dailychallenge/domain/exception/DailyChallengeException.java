package sopt.org.HMH.domain.dailychallenge.domain.exception;

import sopt.org.HMH.domain.challenge.domain.exception.ChallengeError;
import sopt.org.HMH.global.common.exception.base.ExceptionBase;

public class DailyChallengeException extends ExceptionBase {

    public DailyChallengeException(DailyChallengeError error) {
        super(error);
    }
}