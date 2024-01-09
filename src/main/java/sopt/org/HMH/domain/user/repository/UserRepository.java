package sopt.org.HMH.domain.user.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.user.User;
import sopt.org.HMH.domain.user.exception.UserError;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findByIdOrThrowException(Long userId) {
        return findById(userId).orElseThrow(() -> new EntityNotFoundException(UserError.USER_NOT_FOUND.getErrorMessage()));
    }
}
