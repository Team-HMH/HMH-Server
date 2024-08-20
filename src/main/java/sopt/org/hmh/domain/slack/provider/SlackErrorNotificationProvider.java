package sopt.org.hmh.domain.slack.provider;

import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
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
public class SlackErrorNotificationProvider {

    @Value("${slack.webhook.serverErrorWebUrl}")
    private String serverErrorWebUrl;

    private final SlackSender slackSender;

    public void sendNotification(SlackStatus status, Exception e, HttpServletRequest request) {
        slackSender.sendSlackNotification(serverErrorWebUrl, status.getTitle(),
                generateErrorSlackAttachment(e, request, status.getColor()));
    }

    private Attachment generateErrorSlackAttachment(Exception e, HttpServletRequest request, String colorCode) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        return Attachment.builder()
                .color(colorCode)
                .title(requestTime + " 발생 오류 로그")
                .fields(List.of(
                        generateSlackField("User Info", "- ID : " + request.getRemoteUser()),
                        generateSlackField("Request URL", "[" + request.getMethod()+"] " + request.getRequestURI()),
                        generateSlackField("Error Info", e.getMessage())))
                .build();
    }

    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}
