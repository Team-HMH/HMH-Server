package sopt.org.hmh.domain.slack.builder;

import com.slack.api.model.Attachment;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sopt.org.hmh.domain.slack.SlackSender;
import sopt.org.hmh.domain.slack.constant.SlackStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ErrorSlackMessageBuilder implements SlackMessageBuilder {

    @Value("${slack.webhook.serverErrorWebUrl}")
    private String serverErrorWebUrl;

    private final SlackSender slackSender;

    public void sendNotification(SlackStatus status, Exception e, HttpServletRequest request) {
        slackSender.sendSlackNotification(serverErrorWebUrl, status.getTitle(),
                generateSlackAttachment(status, e, request));
    }

    @Override
    public Attachment generateSlackAttachment(SlackStatus status, Object... params) {
        Exception e = (Exception) params[0];
        HttpServletRequest request = (HttpServletRequest) params[1];

        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());

        return Attachment.builder()
                .color(changeColorToHex(status.getColor()))
                .title(requestTime + " 발생 오류 로그")
                .fields(List.of(
                        generateSlackField("User Info", "- ID : " + request.getRemoteUser()),
                        generateSlackField("Request URL", "[" + request.getMethod() + "] " + request.getRequestURI()),
                        generateSlackField("Error Info", e.getMessage())))
                .build();
    }
}
