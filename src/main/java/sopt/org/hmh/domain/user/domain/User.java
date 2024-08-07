package sopt.org.hmh.domain.user.domain;

import static sopt.org.hmh.domain.user.domain.UserConstants.MEMBER_INFO_RETENTION_PERIOD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.org.hmh.domain.user.domain.exception.UserError;
import sopt.org.hmh.domain.user.domain.exception.UserException;
import sopt.org.hmh.global.auth.social.SocialPlatform;
import sopt.org.hmh.global.common.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long currentChallengeId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "소셜 플랫폼은 null일 수 없습니다.")
    private SocialPlatform socialPlatform;

    @Column(unique = true)
    private String socialId;

    @Min(value = 0)
    @NotNull(message = "포인트는 기본 값이 설정되어야 합니다.")
    private Integer point;

    private LocalDate recentLockDate;

    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    @Builder
    public User(SocialPlatform socialPlatform, String socialId, String name) {
        this.socialPlatform = socialPlatform;
        this.socialId = socialId;
        this.name = name;
        this.point = UserConstants.INITIAL_POINT;
    }

    public void updateNickname(String nickname) {
        this.name = nickname;
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

    public Integer decreasePoint(Integer usagePoint) {
        if (this.point < usagePoint) {
            throw new UserException(UserError.NOT_ENOUGH_POINTS);
        }
        this.point -= usagePoint;
        return this.point;
    }

    public Integer increasePoint(Integer earnedPoint) {
        this.point += earnedPoint;
        return this.point;
    }

    public void changePoint(Integer point) {
        this.point = point;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeCurrentChallengeId(Long currentChallengeId) {
        this.currentChallengeId = currentChallengeId;
    }

    public void changeRecentLockDate(LocalDate recentLockDate) {
        this.recentLockDate = recentLockDate;
    }
}