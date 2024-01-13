package sopt.org.HMH.domain.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.domain.user.domain.exception.UserError;
import sopt.org.HMH.domain.user.domain.exception.UserException;
import sopt.org.HMH.global.auth.social.SocialPlatform;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findBySocialPlatformAndSocialIdOrThrowException(SocialPlatform socialPlatform, String socialId) {
        return findBySocialPlatformAndSocialId(socialPlatform, socialId).orElseThrow(() -> new UserException(
                UserError.NOT_SIGNUP_USER));
    }

    default User findByIdOrThrowException(Long userId) {
        return findById(userId).orElseThrow(() -> new UserException(
                UserError.NOT_FOUND_USER));
    }

    @Query("DELETE FROM User u WHERE u.isDeleted = true AND u.deletedAt < :currentDate")
    void deleteUsersScheduledForDeletion(LocalDateTime currentDate);

    Optional<User> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);
    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);
}