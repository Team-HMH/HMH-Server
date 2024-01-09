package sopt.org.HMH.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.domain.user.domain.exception.UserError;
import sopt.org.HMH.domain.user.domain.exception.UserException;
import sopt.org.HMH.global.auth.social.SocialPlatform;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findBySocialPlatformAndSocialIdOrThrowException(SocialPlatform socialPlatform, Long socialId) {
        return findBySocialPlatformAndSocialId(socialPlatform, socialId).orElseThrow(() -> new UserException(
                UserError.NOT_SIGNUP_USER));
    }

    default User findByIdOrThrowException(Long userId) {
        return findById(userId).orElseThrow(() -> new UserException(UserError.NOT_FOUND_USER));
    }

    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, Long socialId);
    Optional<User> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, Long socialId);
}