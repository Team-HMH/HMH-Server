package sopt.org.hmh.global.common.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sopt.org.hmh.global.common.exception.base.ErrorBase;
import sopt.org.hmh.global.common.exception.base.ExceptionBase;
import sopt.org.hmh.global.common.response.BaseResponse;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {

    @ExceptionHandler(value = ExceptionBase.class)
    public ResponseEntity<?> exceptionHandler(HttpServletResponse response, ExceptionBase exception) {
        ErrorBase error = exception.getError();
        response.setStatus(error.getHttpStatusCode());
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(
                        BaseResponse.error(error)
                );
    }
}