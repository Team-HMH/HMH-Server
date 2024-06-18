package sopt.org.hmh.domain.challenge.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeError;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeException;
import sopt.org.hmh.domain.challenge.repository.ChallengeRepository;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Transactional
    public void deleteChallengeRelatedByUserId(List<Long> expiredUserIdList) {
        challengeRepository.deleteByUserIdIn(expiredUserIdList);
    }

    public Challenge findByIdOrElseThrow(Long challengeId) {
        return challengeRepository.findById(challengeId).orElseThrow(
                () -> new ChallengeException(ChallengeError.CHALLENGE_NOT_FOUND));
    }

    public List<ChallengeApp> getCurrentChallengeAppByChallengeId(Long challengeId) {
        return this.findByIdOrElseThrow(challengeId).getApps();
    }

    public Challenge save(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

}
