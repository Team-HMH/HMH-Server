package sopt.org.hmh.domain.challenge.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sopt.org.hmh.domain.challenge.domain.Challenge;

@Repository
@RequiredArgsConstructor
public class ChallengeRepositoryImpl implements ChallengeRepository{

    private final ChallengeJpaRepository challengeJpaRepository;

    @Override
    public Challenge save(Challenge challenge) {
        return challengeJpaRepository.save(challenge);
    }

    @Override
    public Optional<Challenge> findById(Long id) {
        return challengeJpaRepository.findById(id);
    }

    @Override
    public void deleteByUserIdIn(List<Long> userId) {
        challengeJpaRepository.deleteByUserIdIn(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        challengeJpaRepository.deleteByUserId(userId);
    }

}
