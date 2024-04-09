package sopt.org.hmh.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private String averageUseTime;
    private Long userId;

    @Builder
    public OnboardingInfo(String averageUseTime, Long userId) {
        this.averageUseTime = averageUseTime;
        this.userId = userId;
    }
}
