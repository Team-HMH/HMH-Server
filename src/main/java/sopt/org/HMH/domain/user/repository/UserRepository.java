package sopt.org.HMH.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.user.domain.User;
import sopt.org.HMH.global.auth.social.SocialPlatform;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, Long socialId);

    Optional<User> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, Long socialId);
}
