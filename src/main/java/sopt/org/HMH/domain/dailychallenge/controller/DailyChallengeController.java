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
import sopt.org.HMH.global.common.response.ApiResponse;
import sopt.org.HMH.global.common.response.EmptyJsonResponse;
import sopt.org.HMH.global.util.IdConverter;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dailychallenge")
public class DailyChallengeController {

    private final DailyChallengeService dailyChallengeService;

    @GetMapping
    public ResponseEntity<ApiResponse<DailyChallengeResponse>> orderDetailDailyChallenge(Principal principal,
                                                                                         @RequestHeader("OS") final String os) {
        return ResponseEntity
                .status(DailyChallengeSuccess.GET_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(DailyChallengeSuccess.GET_DAILY_CHALLENGE_SUCCESS,
                        dailyChallengeService.getDailyChallenge(IdConverter.getUserId(principal), os)));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<?>> orderModifyDailyChallenge(
            Principal principal,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayUsageTimeRequest request
    ) {
        dailyChallengeService.modifyDailyChallengeStatus(IdConverter.getUserId(principal), request.apps(), os);
        return ResponseEntity
                .status(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_SUCCESS, new EmptyJsonResponse()));
    }

    @PatchMapping("/failure")
    public ResponseEntity<ApiResponse<?>> orderModifyDailyChallengeStatusFailure(Principal principal) {
        dailyChallengeService.modifyDailyChallengeStatusFailure(IdConverter.getUserId(principal));
        return ResponseEntity
                .status(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(DailyChallengeSuccess.MODIFY_DAILY_CHALLENGE_STATUS_FAILURE_SUCCESS, new EmptyJsonResponse()));
    }
}
