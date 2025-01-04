package sopt.org.hmh.domain.slack.builder;

import com.slack.api.model.Attachment;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import sopt.org.hmh.domain.auth.exception.AuthError;
import sopt.org.hmh.domain.slack.SlackSender;
import sopt.org.hmh.domain.slack.constant.SlackStatus;
import sopt.org.hmh.global.auth.jwt.exception.JwtError;
import sopt.org.hmh.global.common.exception.base.ErrorBase;
import sopt.org.hmh.global.common.exception.base.ExceptionBase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ErrorSlackMessageBuilder implements SlackMessageBuilder {

    @Value("${slack.webhook.serverErrorWebUrl}")
    private String serverErrorWebUrl;

    private final SlackSender slackSender;

    private static final Set<String> EXCLUDED_ERROR_MESSAGES = Set.of(
            JwtError.INVALID_ACCESS_TOKEN.getErrorMessage(),
            JwtError.INVALID_SOCIAL_ACCESS_TOKEN.getErrorMessage(),
            AuthError.NOT_SIGNUP_USER.getErrorMessage(),
            AuthError.DUPLICATE_USER.getErrorMessage()
    );

    private boolean shouldSendNotification(ErrorBase e) {
        return !EXCLUDED_ERROR_MESSAGES.contains(e.getErrorMessage());
    }

    public void sendNotification(SlackStatus status, Exception exception, HttpServletRequest request) {
        if (exception instanceof ExceptionBase) {
            ErrorBase error = ((ExceptionBase) exception).getError();
            if (shouldSendNotification(error)) {
                sendSlackNotification(status, error.getHttpStatus(), error.getErrorMessage(), request);
            }
        } else if (exception instanceof ServletException) {
            sendSlackNotification(status, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request);
        }
    }

    private void sendSlackNotification(SlackStatus status, HttpStatus statusCode, String errorMessage, HttpServletRequest request) {
        slackSender.sendSlackNotification(serverErrorWebUrl,
                status.getTitle() + " : " + statusCode,
                generateSlackAttachment(status, errorMessage, request));
    }

    @Override
    public Attachment generateSlackAttachment(SlackStatus status, Object... params) {
        String errorMessage = (String) params[0];
        HttpServletRequest request = (HttpServletRequest) params[1];
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        return Attachment.builder()
                .color(changeColorToHex(status.getColor()))
                .text(requestTime + " 발생")
                .fields(List.of(
                        generateSlackField("User Info", "- ID : " + request.getRemoteUser()),
                        generateSlackField("Request URL", "[" + request.getMethod() + "] " + request.getRequestURI()),
                        generateSlackField("Error Message", errorMessage)))
                .build();
    }
}
