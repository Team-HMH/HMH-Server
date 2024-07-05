package sopt.org.hmh.domain.admin.dto.request;

import java.time.LocalDate;
import java.util.List;
import sopt.org.hmh.domain.dailychallenge.domain.Status;

public record AdminDailyChallengeRequest(
        Long userId,
        LocalDate startDate,
        List<Status> statuses
) {
}
