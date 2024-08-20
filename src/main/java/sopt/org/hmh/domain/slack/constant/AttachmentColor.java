package sopt.org.hmh.domain.slack.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttachmentColor {

    GREEN("98ff98"),
    ORANGE("ffb700"),
    RED("ff0000");

    private final String color;
}
