package sopt.org.hmh.domain.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt.org.hmh.domain.user.domain.User;
import sopt.org.hmh.global.auth.social.SocialPlatform;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    @Query("SELECT u.id FROM User u WHERE u.deletedAt < :now AND u.isDeleted = true")
    List<Long> findIdByDeletedAtBeforeAndIsDeletedIsTrue(@Param("now") LocalDateTime now);
}