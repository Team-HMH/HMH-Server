package sopt.org.HMH.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.HMH.global.auth.social.SocialPlatform;
import sopt.org.HMH.global.common.constant.PointConstants;
import sopt.org.HMH.global.common.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    private static final Long MEMBER_INFO_RETENTION_PERIOD = 30L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private SocialPlatform socialPlatform;
    private String socialId;
    private Integer point;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    private Long onboardingInfoId;
    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    @Builder
    public User(SocialPlatform socialPlatform, String socialId, String name, Long onboardingInfoId) {
        this.socialPlatform = socialPlatform;
        this.socialId = socialId;
        this.name = name;
        this.onboardingInfoId = onboardingInfoId;
        this.point = PointConstants.INITIAL_POINT.getPoint();
    }

    public void updateSocialInfo(String nickname, String profileImageUrl) {
        this.name = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void softDelete() {
        this.isDeleted = true;
        this.point = 0;
        this.deletedAt = LocalDateTime.now().plusDays(MEMBER_INFO_RETENTION_PERIOD);
    }

    public void recover() {
        this.isDeleted = false;
        this.deletedAt = null;
    }
}