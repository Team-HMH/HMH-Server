package sopt.org.hmh.global.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sopt.org.hmh.domain.slack.builder.ErrorSlackMessageBuilder;
import sopt.org.hmh.domain.slack.constant.SlackStatus;
import sopt.org.hmh.global.common.exception.base.ErrorBase;
import sopt.org.hmh.global.common.exception.base.ExceptionBase;
import sopt.org.hmh.global.common.response.BaseResponse;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {

    private final ErrorSlackMessageBuilder errorSlackMessageBuilder;

    @ExceptionHandler(value = ExceptionBase.class)
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, HttpServletResponse response, ExceptionBase exception) {
        errorSlackMessageBuilder.sendNotification(SlackStatus.CLIENT_ERROR, exception, request);

        ErrorBase error = exception.getError();
        response.setStatus(error.getHttpStatusCode());
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(
                        BaseResponse.error(error)
                );
    }
}