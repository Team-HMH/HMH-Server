package sopt.org.HMH.common.response;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sopt.org.HMH.common.exception.base.ErrorBase;
import sopt.org.HMH.common.exception.base.ExceptionBase;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {

    @ExceptionHandler(value = ExceptionBase.class)
    public ResponseEntity<?> ExceptionHandler(ExceptionBase exception) {
        ErrorBase error = exception.getError();
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(
                        ApiResponse.error(error)
                );
    }

}