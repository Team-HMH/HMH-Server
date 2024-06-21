package sopt.org.hmh.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "평균 사용 시간은 null일 수 없습니다.")
    private String averageUseTime;

    @NotNull(message = "유저 아이디는 null일 수 없습니다.")
    private Long userId;

    @Builder
    public OnboardingInfo(String averageUseTime, Long userId) {
        this.averageUseTime = averageUseTime;
        this.userId = userId;
    }
}
