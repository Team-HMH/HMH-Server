package sopt.org.HMH.domain.user.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sopt.org.HMH.domain.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Transactional
public class ExpiredUserDeleteBatch {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 4 * * ?")
    public void deleteExpiredUser() {
        userRepository.deleteUsersScheduledForDeletion(LocalDateTime.now());
    }
}
