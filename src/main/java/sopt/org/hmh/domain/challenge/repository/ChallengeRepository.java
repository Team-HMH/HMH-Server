package sopt.org.hmh.domain.challenge.repository;

import java.util.List;
import java.util.Optional;
import sopt.org.hmh.domain.challenge.domain.Challenge;

public interface ChallengeRepository {
    Optional<Challenge> findById(Long id);

    void deleteByUserIdIn(List<Long> userId);

    void deleteByUserId(Long userId);

    Challenge save(Challenge challenge);
}