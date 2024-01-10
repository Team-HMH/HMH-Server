package sopt.org.HMH.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
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

    Optional<User> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);
    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);
}