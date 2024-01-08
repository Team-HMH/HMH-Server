package sopt.org.HMH.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.HMH.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
