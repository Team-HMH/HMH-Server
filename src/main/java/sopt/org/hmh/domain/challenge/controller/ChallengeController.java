package sopt.org.hmh.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeSuccess;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
public class ChallengeController implements ChallengeApi {

    private final ChallengeService challengeService;

    @PostMapping
    @Override
    public ResponseEntity<BaseResponse<?>> orderAddChallenge(@UserId final Long userId,
                                                             @RequestHeader("OS") final String os,
                                                             @RequestBody final ChallengeRequest request) {
        challengeService.updateChallengeForPeriodWithInfo(
                challengeService.addChallenge(userId, request.period(), request.goalTime()),
                challengeService.getLastApps(userId),
                os);

        return ResponseEntity
                .status(ChallengeSuccess.ADD_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.ADD_CHALLENGE_SUCCESS, new EmptyJsonResponse()));
    }

    @GetMapping
    @Override
    public ResponseEntity<BaseResponse<ChallengeResponse>> orderGetChallenge(@UserId final Long userId,
                                                                             @RequestHeader("OS") final String os) {
        return ResponseEntity
                .status(ChallengeSuccess.GET_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.GET_CHALLENGE_SUCCESS,
                        challengeService.getChallenge(userId, os)));
    }
}