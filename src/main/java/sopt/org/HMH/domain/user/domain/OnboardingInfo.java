package sopt.org.HMH.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "onboarding_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnboardingInfo {

    @Id
    @Column(name = "onboarding_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String averageUseTime;

    private String problem;
}
