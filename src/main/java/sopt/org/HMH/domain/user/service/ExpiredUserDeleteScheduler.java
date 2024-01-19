package sopt.org.HMH.domain.user.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class ExpiredUserDeleteScheduler {

    private final UserService userService;

    @Scheduled(cron = "0 0 4 * * ?")
    public void deleteExpiredUser() {
        userService.deleteExpiredUser(LocalDateTime.now());
    }
}
