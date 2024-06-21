package sopt.org.hmh.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "problem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "온보딩 정보 아이디는 null일 수 없습니다.")
    private Long onboardingInfoId;
    @NotNull(message = "문제 항목은 null일 수 없습니다.")
    private String problem;

    @Builder
    public OnboardingProblem(Long onboardingInfoId, String problem) {
        this.onboardingInfoId = onboardingInfoId;
        this.problem = problem;
    }
}
