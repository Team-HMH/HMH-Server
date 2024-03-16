package sopt.org.hmh.domain.auth.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.domain.users.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Transactional
public class ExpiredUserDeleteScheduler {

    private final UserRepository userRepository;
    private final ChallengeService challengeService;

    @Scheduled(cron = "0 0 4 * * ?")
    public void deleteExpiredUser() {
        deleteExpiredUser(LocalDateTime.now());
    }

    public void deleteExpiredUser(LocalDateTime currentDate) {
        List<Long> expiredUserList = userRepository.findIdByDeletedAtBeforeAndIsDeletedIsTrue(currentDate);
        userRepository.deleteAllById(expiredUserList);
        challengeService.deleteChallengeRelatedByUserId(expiredUserList);
    }
}
