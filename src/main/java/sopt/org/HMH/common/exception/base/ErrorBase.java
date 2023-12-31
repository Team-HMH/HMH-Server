package sopt.org.HMH.common.exception.base;

import org.springframework.http.HttpStatus;

public interface ErrorBase extends RootEnum{
    int getHttpStatusCode();

    HttpStatus getHttpStatus();
    String getErrorMessage();
}