package sopt.org.hmh.domain.dailychallenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.app.dto.request.AppArrayUsageTimeRequest;
import sopt.org.hmh.domain.dailychallenge.domain.Status;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeSuccess;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dailychallenge")
public class DailyChallengeController implements DailyChallengeApi {

    private final DailyChallengeService dailyChallengeService;

    @PatchMapping
    @Override
    public ResponseEntity<BaseResponse<?>> orderAddHistoryDailyChallenge(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayUsageTimeRequest request
    ) {
        Status status = dailyChallengeService.calculateDailyChallengeStatus(userId, request.apps(), os);
        dailyChallengeService.addHistoryDailyChallenge(userId, status, request.apps(), os);
        return ResponseEntity
                .status(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_SUCCESS, new EmptyJsonResponse()));
    }

    @PatchMapping("/failure")
    @Override
    public ResponseEntity<BaseResponse<?>> orderModifyDailyChallengeStatusFailure(@UserId final Long userId) {
        dailyChallengeService.modifyDailyChallengeStatusFailure(userId);
        return ResponseEntity
                .status(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS, new EmptyJsonResponse()));
    }
}