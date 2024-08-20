package sopt.org.hmh.domain.slack;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SlackStatus {

    NEW_USER_SIGNUP(AttachmentColor.GREEN.getColor(), "🎉 신규 유저 회원 가입 발생"),
    INTERNAL_ERROR(AttachmentColor.ORANGE.getColor(), "🚨 서버 내부 에러 발생"),
    CLIENT_ERROR(AttachmentColor.GREEN.getColor(), "😭 서버 오류 발생");

    private final String color;
    private final String title;
}
