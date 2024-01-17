package sopt.org.HMH.domain.dailychallenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.HMH.domain.app.dto.request.AppArrayUsageTimeRequest;
import sopt.org.HMH.domain.dailychallenge.domain.exception.DailyChallengeSuccess;
import sopt.org.HMH.domain.dailychallenge.dto.response.DailyChallengeResponse;
import sopt.org.HMH.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.HMH.global.auth.UserId;
import sopt.org.HMH.global.common.response.BaseResponse;
import sopt.org.HMH.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dailychallenge")
public class DailyChallengeController {

    private final DailyChallengeService dailyChallengeService;

    @GetMapping
    public ResponseEntity<BaseResponse<DailyChallengeResponse>> orderDetailDailyChallenge(@UserId final Long userId,
                                                                                          @RequestHeader("OS") final String os) {
        return ResponseEntity
                .status(DailyChallengeSuccess.GET_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.GET_DAILY_CHALLENGE_SUCCESS,
                        dailyChallengeService.getDailyChallenge(userId, os)));
    }

    @PatchMapping
    public ResponseEntity<BaseResponse<?>> orderModifyDailyChallenge(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayUsageTimeRequest request
    ) {
        dailyChallengeService.modifyDailyChallengeStatus(userId, request.apps(), os);
        return ResponseEntity
                .status(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_SUCCESS, new EmptyJsonResponse()));
    }

    @PatchMapping("/failure")
    public ResponseEntity<BaseResponse<?>> orderModifyDailyChallengeStatusFailure(@UserId final Long userId) {
        dailyChallengeService.modifyDailyChallengeStatusFailure(userId);
        return ResponseEntity
                .status(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS, new EmptyJsonResponse()));
    }
}