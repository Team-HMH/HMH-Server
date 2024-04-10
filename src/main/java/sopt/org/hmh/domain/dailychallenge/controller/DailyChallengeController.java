package sopt.org.hmh.domain.dailychallenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.hmh.domain.app.dto.request.AppArrayUsageTimeRequest;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeSuccess;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge/daily")
public class DailyChallengeController implements DailyChallengeApi {

    private final DailyChallengeService dailyChallengeService;

    @PostMapping
    @Override
    public ResponseEntity<BaseResponse<?>> orderAddHistoryDailyChallenge(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayUsageTimeRequest request
    ) {
        dailyChallengeService.addHistoryDailyChallenge(userId, request.apps(), os);
        return ResponseEntity
                .status(DailyChallengeSuccess.ADD_HISTORY_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.ADD_HISTORY_DAILY_CHALLENGE_SUCCESS, new EmptyJsonResponse()));
    }
}