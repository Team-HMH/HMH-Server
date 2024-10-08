package sopt.org.hmh.domain.slack.builder;

import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import sopt.org.hmh.domain.slack.constant.SlackStatus;

public interface SlackMessageBuilder {

    Attachment generateSlackAttachment(SlackStatus status, Object... params);

    default String changeColorToHex(java.awt.Color color) {
        return String.format("%06X", (0xFFFFFF & color.getRGB()));
    }

    default Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}
