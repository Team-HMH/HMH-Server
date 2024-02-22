package sopt.org.hmh.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.user.domain.OnboardingInfo;

public interface OnboardingInfoRepository extends JpaRepository<OnboardingInfo, Long> {
}
