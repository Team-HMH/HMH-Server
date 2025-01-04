package sopt.org.hmh.domain.slack.builder;

import com.slack.api.model.Attachment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sopt.org.hmh.domain.slack.SlackSender;
import sopt.org.hmh.domain.slack.constant.SlackStatus;
import sopt.org.hmh.domain.user.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewUserSlackMessageBuilder implements SlackMessageBuilder{

    @Value("${slack.webhook.newUserWebUrl}")
    private String newUserWebUrl;

    private final SlackSender slackSender;
    private final UserRepository userRepository;

    public void sendNotification(SlackStatus status, String userName, String os) {
        slackSender.sendSlackNotification(
                newUserWebUrl,
                status.getTitle() + " : 회원 수 " + userRepository.count() + "명",
                generateSlackAttachment(status, userName, os));
    }

    @Override
    public Attachment generateSlackAttachment(SlackStatus status, Object... params) {
        String userName = (String) params[0];
        String os = (String) params[1];

        return Attachment.builder()
                .color(changeColorToHex(status.getColor()))
                .fields(List.of(
                        generateSlackField("User Name", userName),
                        generateSlackField("User OS", os)))
                .build();
    }
}
