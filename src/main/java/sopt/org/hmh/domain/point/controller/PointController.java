package sopt.org.hmh.domain.point.controller;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeSuccess;
import sopt.org.hmh.domain.point.service.PointFacade;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/point")
public class PointController {

    private final PointFacade pointFacade;

    @PatchMapping("/use")
    public ResponseEntity<BaseResponse<?>> orderChallengeFailureByUsagePoint(
            @UserId final Long userId,
            @RequestBody final LocalDate challengeDate
    ) {
        return ResponseEntity
                .status(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS, pointFacade.failChallengeByUsagePoint(userId, challengeDate)));
    }
}
