package sopt.org.hmh.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.app.domain.exception.AppSuccess;
import sopt.org.hmh.domain.app.dto.request.AppArrayGoalTimeRequest;
import sopt.org.hmh.domain.app.dto.request.AppRemoveRequest;
import sopt.org.hmh.domain.challenge.domain.Challenge;
import sopt.org.hmh.domain.challenge.domain.exception.ChallengeSuccess;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.repository.ChallengeRepository;
import sopt.org.hmh.domain.challenge.service.ChallengeService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
public class ChallengeController implements ChallengeApi {

    private final ChallengeService challengeService;
    private final ChallengeRepository challengeRepository;

    @PostMapping
    @Override
    public ResponseEntity<BaseResponse<?>> orderAddChallenge(@UserId final Long userId,
                                                             @RequestHeader("OS") final String os,
                                                             @RequestBody final ChallengeRequest request) {
        challengeService.addChallenge(userId, request.period(), request.goalTime());

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
                        challengeService.getChallenge(userId)));
    }

    @GetMapping("/home")
    @Override
    public ResponseEntity<BaseResponse<ChallengeResponse>> orderGetDailyChallenge(@UserId final Long userId,
                                                                                  @RequestHeader("OS") final String os) {
        return ResponseEntity
                .status(ChallengeSuccess.GET_CHALLENGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(ChallengeSuccess.GET_CHALLENGE_SUCCESS,
                        challengeService.getChallenge(userId)));
    }
}