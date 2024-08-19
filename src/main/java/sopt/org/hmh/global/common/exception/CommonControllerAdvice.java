package sopt.org.hmh.global.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sopt.org.hmh.domain.slack.SlackSender;
import sopt.org.hmh.global.common.exception.base.ErrorBase;
import sopt.org.hmh.global.common.exception.base.ExceptionBase;
import sopt.org.hmh.global.common.response.BaseResponse;

import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {

    private final SlackSender slackSender;

    @ExceptionHandler(value = ExceptionBase.class)
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, HttpServletResponse response, ExceptionBase exception) {
        if (!Objects.equals(request.getRemoteAddr(), "0:0:0:0:0:0:0:1")) // 로컬에서 오류 발생 시 전송 X
            slackSender.sendNotificationServerRuntimeException(exception, request);

        ErrorBase error = exception.getError();
        response.setStatus(error.getHttpStatusCode());
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(
                        BaseResponse.error(error)
                );
    }
}