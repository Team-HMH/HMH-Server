package sopt.org.hmh.global.common.exception.base;

import org.springframework.http.HttpStatus;

public interface SuccessBase extends RootEnum {

    int getHttpStatusCode();

    HttpStatus getHttpStatus();

    String getSuccessMessage();
}