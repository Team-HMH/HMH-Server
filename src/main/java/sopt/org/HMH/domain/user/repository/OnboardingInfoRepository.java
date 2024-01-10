package sopt.org.HMH.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.user.domain.OnboardingInfo;

public interface OnboardingInfoRepository extends JpaRepository<OnboardingInfo, Long> {
}
