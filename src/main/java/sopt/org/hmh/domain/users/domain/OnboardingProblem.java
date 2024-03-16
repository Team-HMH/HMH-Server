package sopt.org.hmh.domain.users.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Long onboardingInfoId;
    private String problem;

    @Builder
    public OnboardingProblem(Long onboardingInfoId, String problem) {
        this.onboardingInfoId = onboardingInfoId;
        this.problem = problem;
    }
}
