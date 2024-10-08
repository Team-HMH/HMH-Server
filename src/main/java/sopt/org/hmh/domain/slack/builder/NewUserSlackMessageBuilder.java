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
                status.getTitle(),
                generateSlackAttachment(status, userName, os));
    }

    @Override
    public Attachment generateSlackAttachment(SlackStatus status, Object... params) {
        String userName = (String) params[0];
        String os = (String) params[1];

        return Attachment.builder()
                .color(changeColorToHex(status.getColor()))
                .title("새로운 유저 '" + userName + "'님이 가입했습니다!")
                .fields(List.of(
                        generateSlackField("총 유저 수", " 👉 " + userRepository.count() + "명"),
                        generateSlackField("가입한 OS", "👉 " + os)))
                .build();
    }
}
