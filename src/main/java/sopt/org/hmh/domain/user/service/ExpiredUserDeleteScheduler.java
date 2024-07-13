package sopt.org.hmh.domain.user.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.domain.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class ExpiredUserDeleteScheduler {

    private final UserRepository userRepository;
    private final ChallengeService challengeService;

    @Scheduled(cron = "0 0 4 * * ?")
    @Transactional
    public void deleteExpiredUser() {
        deleteExpiredUser(LocalDateTime.now());
    }

    public void deleteExpiredUser(LocalDateTime currentDate) {
        List<Long> expiredUserList = userRepository.findIdByDeletedAtBeforeAndIsDeletedIsTrue(currentDate);
        userRepository.deleteAllById(expiredUserList);
        challengeService.deleteChallengeRelatedByUserIds(expiredUserList);
    }
}
