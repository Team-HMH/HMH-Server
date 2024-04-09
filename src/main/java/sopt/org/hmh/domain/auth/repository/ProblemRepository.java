package sopt.org.hmh.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.user.domain.OnboardingProblem;

public interface ProblemRepository extends JpaRepository<OnboardingProblem, Long> {
}
