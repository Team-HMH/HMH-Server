package sopt.org.HMH.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.domain.challenge.domain.Challenge;
import sopt.org.HMH.global.auth.social.SocialPlatform;
import sopt.org.HMH.global.common.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "social_platform")
    @Enumerated(EnumType.STRING)
    private SocialPlatform socialPlatform;

    @Column(name = "social_id")
    private Long socialId;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToOne
    @JoinColumn(name = "onboarding_info_id")
    private OnboardingInfo onboardingInfo;

    @OneToMany(mappedBy = "user")
    private List<Challenge> challenges;

    public void updateSocialInfo(String nickname, String profileImageUrl) {
        this.name = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}