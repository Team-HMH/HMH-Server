package sopt.org.hmh.global.auth.redis;

import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<RefreshToken, Long> {
}