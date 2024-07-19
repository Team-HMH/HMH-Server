package sopt.org.hmh.domain.dailychallenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeSuccess;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeListRequestDeprecated;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeStatusListRequestDeprecated;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeFacadeDeprecated;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@Deprecated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge/daily")
public class DailyChallengeControllerDeprecated implements DailyChallengeApiDeprecated {

    private final DailyChallengeFacadeDeprecated dailyChallengeFacadeDeprecated;

    @Override
    @PostMapping("/finish")
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderAddHistoryDailyChallenge(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody @Valid final FinishedDailyChallengeListRequestDeprecated request
    ) {
        dailyChallengeFacadeDeprecated.addFinishedDailyChallengeHistory(userId, request, os);
        return ResponseEntity
                .status(DailyChallengeSuccess.SEND_FINISHED_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.SEND_FINISHED_DAILY_CHALLENGE_SUCCESS,
                        new EmptyJsonResponse()));
    }

    @Override
    @PostMapping("/success")
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderChangeStatusDailyChallenge(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final FinishedDailyChallengeStatusListRequestDeprecated request
    ) {
        dailyChallengeFacadeDeprecated.changeDailyChallengeStatusByIsSuccess(userId, request);
        return ResponseEntity
                .status(DailyChallengeSuccess.SEND_FINISHED_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.SEND_FINISHED_DAILY_CHALLENGE_SUCCESS,
                        new EmptyJsonResponse()));
    }
}