package sopt.org.hmh.domain.challenge.controller;

import static sopt.org.hmh.domain.challenge.dto.request.NewChallengeOrder.createNextChallengeOrder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeSuccess;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.domain.challenge.service.ChallengeFacade;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.constant.CustomHeaderType;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/challenge")
public class ChallengeController implements ChallengeApi {

    private final ChallengeFacade challengeFacade;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderAddChallenge(
            @UserId final Long userId,
            @RequestHeader(CustomHeaderType.OS) final String os,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone,
            @RequestBody @Valid final ChallengeRequest request) {
        challengeFacade.startNewChallenge(createNextChallengeOrder(request, userId, os, timeZone));
        return ResponseEntity
                .status(ChallengeSuccess.ADD_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.ADD_CHALLENGE_SUCCESS, new EmptyJsonResponse()));
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<ChallengeResponse>> orderGetChallenge(
            @UserId final Long userId,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone) {
        return ResponseEntity
                .status(ChallengeSuccess.GET_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.GET_CHALLENGE_SUCCESS,
                        challengeFacade.getCurrentChallengeInfo(userId, timeZone)));
    }

    @Override
    @GetMapping("/home")
    public ResponseEntity<BaseResponse<DailyChallengeResponse>> orderGetDailyChallenge(
            @UserId final Long userId,
            @RequestHeader(CustomHeaderType.TIME_ZONE) final String timeZone) {
        return ResponseEntity
                .status(ChallengeSuccess.GET_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.GET_DAILY_CHALLENGE_SUCCESS,
                        challengeFacade.getDailyChallengeInfo(userId, timeZone)));
    }
}