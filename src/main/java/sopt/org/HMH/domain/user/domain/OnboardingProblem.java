package sopt.org.HMH.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "problem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingProblem {

    @Id
    @Column(name = "problem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String problem;

    @Builder
    public OnboardingProblem(String problem) {
        this.problem = problem;
    }
}
