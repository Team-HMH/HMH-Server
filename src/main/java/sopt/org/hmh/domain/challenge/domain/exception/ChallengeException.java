package sopt.org.hmh.domain.challenge.domain.exception;

import sopt.org.hmh.global.common.exception.base.ExceptionBase;

public class ChallengeException extends ExceptionBase {

    public ChallengeException(ChallengeError error) {
        super(error);
    }
}