package sopt.org.HMH.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.user.domain.OnboardingProblem;

public interface ProblemRepository extends JpaRepository<OnboardingProblem, Long> {
}
