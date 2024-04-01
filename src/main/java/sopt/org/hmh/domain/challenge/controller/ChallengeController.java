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

    @PostMapping("/app")
    @Override
    public ResponseEntity<BaseResponse<?>> orderAddApps(@UserId final Long userId,
                                                        @RequestHeader("OS") final String os,
                                                        @RequestBody final AppArrayGoalTimeRequest requests) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
        challengeService.addApps(challenge, requests.apps(), os);

        return ResponseEntity
                .status(AppSuccess.ADD_APP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AppSuccess.ADD_APP_SUCCESS, new EmptyJsonResponse()));

    }

    @GetMapping("/app")
    @Override
    public ResponseEntity<BaseResponse<?>> orderRemoveApp(@UserId final Long userId,
                                                          @RequestHeader("OS") final String os,
                                                          @RequestBody final AppRemoveRequest request) {
        Challenge challenge = challengeRepository.findFirstByUserIdOrderByCreatedAtDescOrElseThrow(userId);
        challengeService.removeApp(challenge, request, os);

        return ResponseEntity
                .status(AppSuccess.REMOVE_APP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AppSuccess.REMOVE_APP_SUCCESS, new EmptyJsonResponse()));
    }
}