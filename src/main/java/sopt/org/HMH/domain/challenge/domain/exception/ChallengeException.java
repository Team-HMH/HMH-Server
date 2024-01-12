package sopt.org.HMH.domain.challenge.domain.exception;

import sopt.org.HMH.global.common.exception.base.ExceptionBase;

public class ChallengeException extends ExceptionBase {

    public ChallengeException(ChallengeError error) {
        super(error);
    }
}