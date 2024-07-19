package sopt.org.hmh.domain.dailychallenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.service.ChallengeFacade;
import sopt.org.hmh.domain.dailychallenge.domain.exception.DailyChallengeSuccess;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeListRequest;
import sopt.org.hmh.domain.dailychallenge.dto.request.FinishedDailyChallengeStatusListRequest;
import sopt.org.hmh.domain.dailychallenge.dto.response.ChallengeStatusesResponse;
import sopt.org.hmh.domain.dailychallenge.service.DailyChallengeFacade;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.constant.CustomHeaderType;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/challenge/daily")
public class DailyChallengeController implements DailyChallengeApi {

    private final DailyChallengeFacade dailyChallengeFacade;

    @Override
    @PostMapping("/finish")
    public ResponseEntity<BaseResponse<ChallengeStatusesResponse>> orderAddHistoryDailyChallenge(
            @UserId final Long userId,
            @RequestHeader(CustomHeaderType.OS) final String os,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone,
            @RequestBody @Valid final FinishedDailyChallengeListRequest request
    ) {
        dailyChallengeFacade.addFinishedDailyChallengeHistory(userId, request, os);
        return ResponseEntity
                .status(DailyChallengeSuccess.SEND_FINISHED_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.SEND_FINISHED_DAILY_CHALLENGE_SUCCESS,
                        new ChallengeStatusesResponse(dailyChallengeFacade.getChangedChallengeStatuses(userId))));
    }

    @Override
    @PostMapping("/success")
    public ResponseEntity<BaseResponse<ChallengeStatusesResponse>> orderChangeStatusDailyChallenge(
            @UserId final Long userId,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone,
            @RequestBody final FinishedDailyChallengeStatusListRequest request
    ) {
        dailyChallengeFacade.changeDailyChallengeStatusByIsSuccess(userId, request);
        return ResponseEntity
                .status(DailyChallengeSuccess.SEND_FINISHED_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(DailyChallengeSuccess.SEND_FINISHED_DAILY_CHALLENGE_SUCCESS,
                        new ChallengeStatusesResponse(dailyChallengeFacade.getChangedChallengeStatuses(userId))));
    }
}