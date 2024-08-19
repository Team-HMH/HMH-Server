package sopt.org.hmh.domain.slack;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import com.slack.api.webhook.WebhookPayloads;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sopt.org.hmh.domain.user.repository.UserRepository;
import sopt.org.hmh.global.common.exception.base.ExceptionBase;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackSender {

    private final Slack slackClient = Slack.getInstance();
    private final UserRepository userRepository;

    @Value("${slack.webhook.serverErrorWebUrl}")
    private String serverErrorWebUrl;

    @Value("${slack.webhook.newUserWebUrl}")
    private String newUserWebUrl;

    public void sendNotificationNewUser(String userName, String os) {
       sendSlackNotification(newUserWebUrl, "🎉 신규 유저 회원 가입 발생", generateNewUserSlackAttachment(userName, os));
    }

    public void sendNotificationServerInternalError(Exception e, HttpServletRequest request) {
        sendSlackNotification(serverErrorWebUrl, "🚨 서버 내부 에러 발생", generateInternalErrorSlackAttachment(e, request));
    }

    public void sendNotificationServerRuntimeException(ExceptionBase e, HttpServletRequest request) {
        sendSlackNotification(serverErrorWebUrl, "😭 서버 오류 발생", generateRuntimeExceptionSlackAttachment(e, request));
    }

    private void sendSlackNotification(String webhookUrl, String title, Attachment attachment) {
        try {
            slackClient.send(webhookUrl, WebhookPayloads.payload(p -> p
                    .text(title)
                    .attachments(List.of(attachment))
            ));
        } catch (IOException slackError) {
            log.debug("Slack 통신 예외 발생: {}", slackError.getMessage());
        }
    }

    private Attachment generateNewUserSlackAttachment(String userName, String os) {
        return Attachment.builder()
                .color("98ff98")  // 초록색
                .title("새로운 유저 '" + userName + "'님이 가입했습니다!")
                .fields(List.of(
                        generateSlackField("총 유저 수", " 👉 " + userRepository.count() + "명"),
                        generateSlackField("가입한 OS", "👉 " + os)))
                .build();
    }

    private Attachment generateInternalErrorSlackAttachment(Exception e, HttpServletRequest request) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        return Attachment.builder()
                .color("ff0000")  // 붉은색
                .title(requestTime + " 발생 오류 로그")
                .fields(List.of(
                                generateSlackField("User Info", "- ID : " + request.getRemoteUser()
                                        + " \n- IP : " + request.getRemoteAddr()),
                                generateSlackField("Request URL", request.getRequestURI() + " [" + request.getMethod()+"]"),
                                generateSlackField("Error Message", "[500] " + e.getMessage())))
                .build();
    }

    private Attachment generateRuntimeExceptionSlackAttachment(ExceptionBase e, HttpServletRequest request) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        return Attachment.builder()
                .color("ffb700")  // 주황색
                .title(requestTime + " 발생 오류 로그")
                .fields(List.of(
                        generateSlackField("User Info", "- ID : " + request.getRemoteUser()
                                + " \n- IP : " + request.getRemoteAddr()
                                + " \n- TOKEN: " + request.getHeader("Authorization")),
                        generateSlackField("Request URL", "[" + request.getMethod()+"] " + request.getRequestURI()),
                        generateSlackField("Error Info", "[" + e.getError().getHttpStatusCode() + "] " + e.getMessage())))
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
