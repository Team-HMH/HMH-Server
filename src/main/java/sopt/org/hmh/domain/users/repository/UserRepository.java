package sopt.org.hmh.domain.users.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt.org.hmh.domain.users.domain.User;
import sopt.org.hmh.domain.auth.exception.AuthError;
import sopt.org.hmh.domain.auth.exception.AuthException;
import sopt.org.hmh.domain.users.domain.exception.UserError;
import sopt.org.hmh.domain.users.domain.exception.UserException;
import sopt.org.hmh.global.auth.social.SocialPlatform;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findBySocialPlatformAndSocialIdOrThrowException(SocialPlatform socialPlatform, String socialId) {
        return findBySocialPlatformAndSocialId(socialPlatform, socialId).orElseThrow(() -> new AuthException(
                AuthError.NOT_SIGNUP_USER));
    }

    default User findByIdOrThrowException(Long userId) {
        return findById(userId).orElseThrow(() -> new UserException(
                UserError.NOT_FOUND_USER));
    }

    @Query("SELECT u.id FROM User u WHERE u.deletedAt < :now AND u.isDeleted = true")
    List<Long> findIdByDeletedAtBeforeAndIsDeletedIsTrue(@Param("now") LocalDateTime now);

    Optional<User> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);
}