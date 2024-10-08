package sopt.org.hmh.domain.slack.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SlackStatus {
    NEW_USER_SIGNUP(java.awt.Color.GREEN, "🎉 신규 유저 회원 가입 발생"),
    INTERNAL_ERROR(java.awt.Color.ORANGE, "🚨 서버 내부 에러 발생"),
    CLIENT_ERROR(java.awt.Color.RED, "😭 서버 오류 발생");

    private final java.awt.Color color;
    private final String title;
}
