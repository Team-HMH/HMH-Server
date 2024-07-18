package sopt.org.hmh.domain.challenge.controller;

import static sopt.org.hmh.domain.challenge.dto.request.NewChallengeOrder.createNextChallengeOrder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.hmh.domain.app.domain.exception.AppSuccess;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppArrayRequest;
import sopt.org.hmh.domain.app.dto.request.AppRemoveRequest;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeSuccess;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.domain.challenge.service.ChallengeFacade;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
public class ChallengeControllerDeprecated implements ChallengeApiDeprecated {

    private final ChallengeFacade challengeFacade;

    @PostMapping
    @Override
    @Deprecated
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderAddChallenge(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody @Valid final ChallengeRequest request) {
        challengeFacade.startNewChallenge(createNextChallengeOrder(request, userId, os, "Asia/Seoul"));

        return ResponseEntity
                .status(ChallengeSuccess.ADD_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.ADD_CHALLENGE_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping
    @Override
    @Deprecated
    public ResponseEntity<BaseResponse<ChallengeResponse>> orderGetChallenge(
            @UserId final Long userId) {
        return ResponseEntity
                .status(ChallengeSuccess.GET_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.GET_CHALLENGE_SUCCESS,
                        challengeFacade.getCurrentChallengeInfo(userId)));
    }

    @GetMapping("/home")
    @Override
    @Deprecated
    public ResponseEntity<BaseResponse<DailyChallengeResponse>> orderGetDailyChallenge(
            @UserId final Long userId) {
        return ResponseEntity
                .status(ChallengeSuccess.GET_DAILY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.GET_DAILY_CHALLENGE_SUCCESS,
                        challengeFacade.getDailyChallengeInfo(userId, "Asia/Seoul")));
    }

    @PostMapping("/app")
    @Override
    @Deprecated
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderAddApps(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody @Valid final ChallengeAppArrayRequest requests) {
        challengeFacade.addAppsToCurrentChallenge(userId, requests.apps(), os);

        return ResponseEntity
                .status(AppSuccess.ADD_APP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AppSuccess.ADD_APP_SUCCESS, new EmptyJsonResponse()));

    }

    @DeleteMapping("/app")
    @Override
    @Deprecated
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderRemoveApp(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody @Valid final AppRemoveRequest request) {
        challengeFacade.removeAppFromCurrentChallenge(userId, request.appCode(), os);

        return ResponseEntity
                .status(AppSuccess.REMOVE_APP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AppSuccess.REMOVE_APP_SUCCESS, new EmptyJsonResponse()));
    }
}