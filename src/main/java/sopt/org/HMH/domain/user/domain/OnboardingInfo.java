package sopt.org.HMH.domain.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingInfo {

    @Id
    @Column(name = "onboarding_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "onboarding_info_id")
    private List<OnboardingProblem> problem;

    private String averageUseTime;

    @Builder
    public OnboardingInfo(List<OnboardingProblem> problem, String averageUseTime) {
        this.problem = problem;
        this.averageUseTime = averageUseTime;
    }
}
