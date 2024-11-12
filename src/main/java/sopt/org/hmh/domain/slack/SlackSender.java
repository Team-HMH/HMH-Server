package sopt.org.hmh.domain.slack;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.webhook.WebhookPayloads;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackSender {

    private final Slack slackClient = Slack.getInstance();

    public void sendSlackNotification(String webhookUrl, String mainText, Attachment attachment) {
        try {
            slackClient.send(webhookUrl, WebhookPayloads.payload(p -> p
                    .text("*" + mainText + "*")
                    .attachments(List.of(attachment))
            ));
        } catch (IOException slackError) {
            log.debug("Slack 통신 예외 발생: {}", slackError.getMessage());
        }
    }
}
