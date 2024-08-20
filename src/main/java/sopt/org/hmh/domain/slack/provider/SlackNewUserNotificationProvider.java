package sopt.org.hmh.domain.slack.provider;

import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sopt.org.hmh.domain.slack.SlackSender;
import sopt.org.hmh.domain.slack.constant.SlackStatus;
import sopt.org.hmh.domain.user.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SlackNewUserNotificationProvider {

    @Value("${slack.webhook.newUserWebUrl}")
    private String newUserWebUrl;

    private final SlackSender slackSender;
    private final UserRepository userRepository;

    public void sendNotification(String userName, String os, SlackStatus status) {
        slackSender.sendSlackNotification(newUserWebUrl, status.getTitle(),
                generateNewUserSlackAttachment(userName, os, status.getColor()));
    }

    public Attachment generateNewUserSlackAttachment(String userName, String os, String colorCode) {
        return Attachment.builder()
                .color(colorCode)
                .title("새로운 유저 '" + userName + "'님이 가입했습니다!")
                .fields(List.of(
                        generateSlackField("총 유저 수", " 👉 " + userRepository.count() + "명"),
                        generateSlackField("가입한 OS", "👉 " + os)))
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
